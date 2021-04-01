package io.joel.springbank.server.controller

import io.joel.springbank.api.request.EchoRequest
import org.springframework.stereotype.Component

@Component
class EchoController {
    @WebSocketRoute
    fun handle(request: EchoRequest) = request
}
