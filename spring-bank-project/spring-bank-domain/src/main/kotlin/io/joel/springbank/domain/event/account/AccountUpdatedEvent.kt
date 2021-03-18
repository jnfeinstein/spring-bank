package io.joel.springbank.domain.event.account

import java.util.*

data class AccountUpdatedEvent(
    val id: UUID,
    val name: String,
)
