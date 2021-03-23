package io.joel.springbank.api.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

actual data class CreateAccountCommand(
    @TargetAggregateIdentifier
    val id: UUID = UUID.randomUUID(),
    actual val name: String,
)
