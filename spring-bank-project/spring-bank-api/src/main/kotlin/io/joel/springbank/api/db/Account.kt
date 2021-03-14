package io.joel.springbank.api.db

import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Table
data class Account(
    @Id var id: Long = 0L,
    var name: String = "",
//    @Version val version: Long = 0L,
)

interface AccountRepo : CoroutineCrudRepository<Account, Long> {
    suspend fun findByNameContains(name: String): Flow<Account>
}
