package io.joel.springbank.api.event

import io.joel.springbank.api.ObjectId
import kotlin.js.JsExport

@JsExport
data class AccountCreatedEvent(
    val id: ObjectId,
    val name: String,
)
