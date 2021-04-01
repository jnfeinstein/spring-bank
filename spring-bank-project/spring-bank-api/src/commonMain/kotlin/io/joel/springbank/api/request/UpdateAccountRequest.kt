package io.joel.springbank.api.request

import io.joel.springbank.api.Request
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class UpdateAccountRequest(
    val id: String,
    val name: String,
) : Request