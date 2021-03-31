package io.joel.springbank.api.command

import kotlinx.serialization.Serializable
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

@Serializable
actual data class CreateAccountCommand(
    @TargetAggregateIdentifier
    val id: String = UUID.randomUUID().toString(),
    actual val name: String,
)
