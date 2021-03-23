package io.joel.springbank.api.command

import io.joel.springbank.api.ObjectId
import kotlin.js.JsExport

@JsExport
data class DeleteAccountCommand(
    val id: ObjectId
)
