package io.joel.springbank.api.db

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

@Table
data class AccountView(
    val id: UUID,
    val name: String,
) : BaseView<UUID>(id)

abstract class BaseView<ID>(
    private val id: ID,
) : Persistable<ID> {
    @Transient
    private var isNew = false

    @Id
    override fun getId() = id

    @JsonIgnore
    override fun isNew() = isNew

    fun markNew() {
        isNew = true
    }
}

interface AccountViewRepo : CoroutineCrudRepository<AccountView, UUID> {
    suspend fun findByNameContains(name: String): Flow<AccountView>
}
