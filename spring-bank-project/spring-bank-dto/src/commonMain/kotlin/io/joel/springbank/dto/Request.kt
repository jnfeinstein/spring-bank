@file:OptIn(ExperimentalJsExport::class)
@file:JsExport
package io.joel.springbank.dto

import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport

object Request {
    data class CreateAccount(
        val name: String,
    )

    data class UpdateAccount(
        val id: String,
        val name: String,
    )
}
