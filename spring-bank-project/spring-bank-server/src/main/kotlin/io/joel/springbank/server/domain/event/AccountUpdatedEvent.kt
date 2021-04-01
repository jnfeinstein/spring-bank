package io.joel.springbank.server.domain.event

import io.joel.springbank.api.response.AccountUpdatedResponse

data class AccountUpdatedEvent(
    val id: String,
    val name: String,
) {
    fun toResponse() = AccountUpdatedResponse(id = id, name = name)
}
