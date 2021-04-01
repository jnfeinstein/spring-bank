package io.joel.springbank.server.domain.command

import io.joel.springbank.api.request.UpdateAccountRequest
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class UpdateAccountCommand(
    @TargetAggregateIdentifier
    val id: String,
    val name: String,
) {
    constructor(request: UpdateAccountRequest) : this(request.id, request.name)
}
