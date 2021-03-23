package io.joel.springbank.server.db

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface AccountViewRepo : CoroutineCrudRepository<AccountView, UUID> {
    suspend fun findByNameContains(name: String): Flow<AccountView>
}
