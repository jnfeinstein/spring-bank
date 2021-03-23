package io.joel.springbank.server.controller

import io.joel.springbank.api.command.EchoCommand
import org.springframework.stereotype.Component

@Component
class EchoController {
    @WebSocketRoute
    fun handle(command: EchoCommand) = command
}
