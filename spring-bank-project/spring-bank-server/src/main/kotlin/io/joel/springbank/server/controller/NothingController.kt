package io.joel.springbank.server.controller

import io.joel.springbank.api.Message
import io.joel.springbank.api.request.NullRequest
import io.joel.springbank.api.request.UnitRequest
import org.springframework.stereotype.Component

@Component
class NothingController {
    @WebSocketRoute
    fun handle(request: NullRequest): Message? = null

    @WebSocketRoute
    fun handle(request: UnitRequest) { }
}
