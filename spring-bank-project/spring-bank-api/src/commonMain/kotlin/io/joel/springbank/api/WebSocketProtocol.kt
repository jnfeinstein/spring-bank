package io.joel.springbank.api

import kotlin.js.JsExport

@JsExport
data class WebSocketProtocol(
    val type: String,
    val payload: Any,
)
