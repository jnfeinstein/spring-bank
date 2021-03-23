package io.joel.springbank.api.command

@JsExport
actual data class CreateAccountCommand(
    actual val name: String,
)
