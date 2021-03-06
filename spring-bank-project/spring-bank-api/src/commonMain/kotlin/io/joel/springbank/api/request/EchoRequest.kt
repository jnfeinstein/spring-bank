package io.joel.springbank.api.request

import io.joel.springbank.api.Request
import kotlinx.serialization.*
import kotlin.js.JsExport

@JsExport
@Serializable
public data class EchoRequest(val message: String) : Request
