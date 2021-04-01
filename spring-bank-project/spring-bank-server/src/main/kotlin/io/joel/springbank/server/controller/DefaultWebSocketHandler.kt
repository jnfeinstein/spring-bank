package io.joel.springbank.server.controller

import io.joel.springbank.api.Format
import io.joel.springbank.api.Message
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
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KFunction
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.typeOf

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
    private val webSocketRoutes: MutableMap<String, Pair<BeanName, KFunction<Message>>> = ConcurrentHashMap()

    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalStdlibApi::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: BeanName) = bean.apply {
        try {
            bean::class.memberFunctions
        } catch (ex: Throwable) {
            return@apply
        }.filter {
            it.hasAnnotation<WebSocketRoute>()
        }.forEach {
            check(it.returnType.classifier == Unit::class || it.returnType.isSubtypeOf(typeOf<Message?>())) {
                "WebSocket route must return type ${Message::class.simpleName}."
            }
            it as KFunction<Message>
            val clazz = it.valueParameters.first().type.jvmErasure
            val existingMapping = webSocketRoutes.putIfAbsent(clazz.simpleName!!.toLowerCase(), beanName to it)
            check(existingMapping == null) {
                "Type ${clazz.simpleName} associated with multiple WebSocket routes."
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
            val args = method.valueParameters.withIndex().map { (idx, parameter) ->
                if (idx == 0) payload else beanFactory.getBean(parameter.type.jvmErasure.java)
            }.toTypedArray()

            mono(context) {
                method.callSuspend(bean, *args)
            }
        }.map { response ->
            session.binaryMessage {
                it.wrap(Format.toProtobuf(response as Message))
            }
        }
    )
}
