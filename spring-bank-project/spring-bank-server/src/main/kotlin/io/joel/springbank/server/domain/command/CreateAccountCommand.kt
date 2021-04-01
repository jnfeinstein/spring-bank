package io.joel.springbank.server.domain.command

import io.joel.springbank.api.request.CreateAccountRequest
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateAccountCommand(
    @TargetAggregateIdentifier
    val id: String,
    val name: String,
) {
    constructor(request: CreateAccountRequest) : this(UUID.randomUUID().toString(), request.name)
}
