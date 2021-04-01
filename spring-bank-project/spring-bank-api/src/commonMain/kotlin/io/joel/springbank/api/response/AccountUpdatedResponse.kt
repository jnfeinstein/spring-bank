package io.joel.springbank.api.response

import io.joel.springbank.api.Response
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class AccountUpdatedResponse(
    val id: String,
    val name: String,
) : Response
