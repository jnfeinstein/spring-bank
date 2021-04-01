package io.joel.springbank.server.domain.event

import io.joel.springbank.api.response.AccountDeletedResponse

data class AccountDeletedEvent(
    val id: String,
) {
    fun toResponse() = AccountDeletedResponse(id = id)
}
