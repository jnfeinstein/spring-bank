package io.joel.springbank.api.command

import kotlinx.serialization.*
import kotlin.js.JsExport

@JsExport
@Serializable
data class EchoCommand(val message: String)
