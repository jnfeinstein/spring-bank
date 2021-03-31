package io.joel.springbank.server.controller

import io.joel.springbank.api.Format
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import kotlinx.serialization.ExperimentalSerializationApi
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.Continuation
import kotlin.reflect.full.callSuspend
import kotlin.reflect.jvm.kotlinFunction

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebSocketRoute

private typealias BeanName = String

@Component
class DefaultWebSocketHandler(
    private val beanFactory: BeanFactory,
    private val context: CoroutineDispatcher = Dispatchers.Default,
) : WebSocketHandler, BeanPostProcessor {
    private val webSocketRoutes: MutableMap<String, Pair<BeanName, Method>> = ConcurrentHashMap()

    override fun postProcessAfterInitialization(bean: Any, beanName: String) = bean.apply {
        bean::class.java.methods.filter {
            it.isAnnotationPresent(WebSocketRoute::class.java)
        }.forEach {
            val clazz = it.parameters.first().type
            val existingMapping = webSocketRoutes.putIfAbsent(clazz.simpleName.toLowerCase(), beanName to it)
            check(existingMapping == null) {
                "Type ${clazz.simpleName} associated with multiple WebSocket routes"
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class, ExperimentalSerializationApi::class)
    override fun handle(session: WebSocketSession) = session.send(
        session.receive().flatMap { message ->
            val payload = Format.fromProtobuf(message.payload.asInputStream().readAllBytes())
            val (beanName, method) = webSocketRoutes[payload::class.simpleName!!.toLowerCase()]
                ?: return@flatMap Mono.just(UnsupportedOperationException())
            val bean = beanFactory.getBean(beanName)
            val args = buildList(method.parameterCount) {
                add(payload)
                for (idx in 1 until method.parameterCount) {
                    if (method.parameters[idx].type != Continuation::class.java) {
                        add(beanFactory.getBean(method.parameters[idx].type))
                    }
                }
            }.toTypedArray()

            mono(context) {
                method.kotlinFunction?.callSuspend(bean, *args) ?: method.invoke(bean, *args)
            }
        }.map { response ->
            session.binaryMessage {
                it.wrap(Format.toProtobuf(response))
            }
        }
    )
}
