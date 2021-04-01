package io.joel.springbank.api.request

import io.joel.springbank.api.Request
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class CreateAccountRequest(
    val name: String,
) : Request
