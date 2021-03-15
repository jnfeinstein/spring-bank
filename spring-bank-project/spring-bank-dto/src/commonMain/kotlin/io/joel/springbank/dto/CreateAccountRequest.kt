@file:OptIn(ExperimentalJsExport::class)
@file:JsExport
package io.joel.springbank.dto

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

data class CreateAccountRequest(
    val name: String,
)
