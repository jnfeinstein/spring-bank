package io.joel.springbank.api.event

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class AccountCreatedEvent(
    val id: String,
    val name: String,
)
