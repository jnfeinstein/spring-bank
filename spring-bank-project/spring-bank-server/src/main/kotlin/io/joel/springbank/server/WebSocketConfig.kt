package io.joel.springbank.server

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.WebSocketService
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig {
    @Bean
    fun handlerMapping(webSocketHandler: WebSocketHandler): HandlerMapping {
        return SimpleUrlHandlerMapping().apply {
            order = Ordered.HIGHEST_PRECEDENCE
            urlMap = mapOf("/" to webSocketHandler)
        }
    }

    @Bean
    fun webSocketService(): WebSocketService {
        return HandshakeWebSocketService()
    }

    @Bean
    fun handlerAdapter(webSocketService: WebSocketService): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter(webSocketService)
    }
}
