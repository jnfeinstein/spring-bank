package io.joel.springbank.domain.command.account

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class UpdateAccountCommand(
    @TargetAggregateIdentifier
    val id: UUID,
    val name: String,
)
