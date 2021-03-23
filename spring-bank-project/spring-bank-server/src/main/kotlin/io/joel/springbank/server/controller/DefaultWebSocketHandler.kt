package io.joel.springbank.server.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.joel.springbank.api.WebSocketProtocol
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
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
    private val objectMapper: ObjectMapper,
    private val context: CoroutineDispatcher = Dispatchers.Default,
) : WebSocketHandler, BeanPostProcessor {
    private val webSocketRoutes: MutableMap<String, Triple<BeanName, Class<*>, Method>> = ConcurrentHashMap()

    override fun postProcessAfterInitialization(bean: Any, beanName: String) = bean.apply {
        bean::class.java.methods.filter {
            it.isAnnotationPresent(WebSocketRoute::class.java)
        }.forEach {
            val clazz = it.parameters.first().type
            val existingMapping = webSocketRoutes.putIfAbsent(clazz.canonicalName.toLowerCase(), Triple(beanName, clazz, it))
            check(existingMapping == null) {
                "Type ${clazz.simpleName} associated with multiple WebSocket routes"
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun handle(session: WebSocketSession) = session.send(
        session.receive().flatMap { message ->
            val protocol = objectMapper.readValue<WebSocketProtocol>(message.payloadAsText)
            val (beanName, clazz, method) = webSocketRoutes[protocol.type.trim().toLowerCase()]
                ?: return@flatMap Mono.just(UnsupportedOperationException())
            val bean = beanFactory.getBean(beanName)
            val payload = objectMapper.convertValue(protocol.payload, clazz)
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
        }.map {
            session.textMessage(
                objectMapper.writeValueAsString(
                    WebSocketProtocol(type = it::class.qualifiedName!!, payload = it)
                )
            )
        }
    )
}
