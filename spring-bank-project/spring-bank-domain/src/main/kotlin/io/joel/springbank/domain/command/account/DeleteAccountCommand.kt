package io.joel.springbank.domain.command.account

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class DeleteAccountCommand(
    @TargetAggregateIdentifier
    val id: UUID,
)
