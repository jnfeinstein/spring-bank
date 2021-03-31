package io.joel.springbank.api.command

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class DeleteAccountCommand(
    val id: String,
)
