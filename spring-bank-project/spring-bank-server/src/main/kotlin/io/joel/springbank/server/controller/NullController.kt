package io.joel.springbank.server.controller

import io.joel.springbank.api.command.NullCommand
import org.springframework.stereotype.Component

@Component
class NullController {
    @WebSocketRoute
    fun handle(command: NullCommand) = null
}
