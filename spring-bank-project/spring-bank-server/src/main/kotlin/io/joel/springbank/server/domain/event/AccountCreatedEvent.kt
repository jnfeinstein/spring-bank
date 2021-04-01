package io.joel.springbank.server.domain.event

import io.joel.springbank.api.response.AccountCreatedResponse

data class AccountCreatedEvent(
    val id: String,
    val name: String,
) {
    fun toResponse() = AccountCreatedResponse(id = id, name = name)
}
