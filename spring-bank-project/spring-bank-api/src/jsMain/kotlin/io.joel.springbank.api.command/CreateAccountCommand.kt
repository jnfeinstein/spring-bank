package io.joel.springbank.api.command

import kotlinx.serialization.Serializable

@JsExport
@Serializable
actual data class CreateAccountCommand(
    actual val name: String,
)
