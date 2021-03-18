package io.joel.springbank.domain.event.account

import java.util.*

data class AccountCreatedEvent(
    val id: UUID,
    val name: String,
)
