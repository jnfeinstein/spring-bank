package io.joel.springbank.api.request

import io.joel.springbank.api.Request
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class DeleteAccountRequest(
    val id: String,
) : Request
