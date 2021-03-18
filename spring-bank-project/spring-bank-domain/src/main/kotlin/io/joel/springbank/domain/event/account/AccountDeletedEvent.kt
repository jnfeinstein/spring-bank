package io.joel.springbank.domain.event.account

import java.util.*

data class AccountDeletedEvent(
    val id: UUID,
)
