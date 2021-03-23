package io.joel.springbank.server.db

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class AccountView(
    @Id val id: UUID,
    val name: String,
)
