package io.joel.springbank.api.command

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class UpdateAccountCommand(
    val id: String,
    val name: String,
)
