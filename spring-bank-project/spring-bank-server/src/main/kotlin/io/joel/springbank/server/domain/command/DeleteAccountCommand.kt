package io.joel.springbank.server.domain.command

import io.joel.springbank.api.request.DeleteAccountRequest
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class DeleteAccountCommand(
    @TargetAggregateIdentifier
    val id: String,
) {
    constructor(request: DeleteAccountRequest) : this(request.id)
}
