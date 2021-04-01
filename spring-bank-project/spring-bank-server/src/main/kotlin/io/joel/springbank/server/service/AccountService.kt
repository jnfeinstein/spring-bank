package io.joel.springbank.server.service

import io.joel.springbank.server.db.AccountView
import io.joel.springbank.server.db.AccountViewRepo
import io.joel.springbank.server.domain.event.AccountCreatedEvent
import io.joel.springbank.server.domain.event.AccountDeletedEvent
import io.joel.springbank.server.domain.event.AccountUpdatedEvent
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    private val accountViewRepo: AccountViewRepo,
    private val connectionFactory: ConnectionFactory,
    private val queryUpdateEmitter: QueryUpdateEmitter,
) {
    @EventHandler
    fun on(event: AccountCreatedEvent) = runBlocking {
        R2dbcEntityTemplate(connectionFactory).insert(
            AccountView(id = UUID.fromString(event.id), name = event.name)
        ).awaitSingle()
        queryUpdateEmitter.emit(String::class.java, { it == event.id }, event)
    }

    @EventHandler
    fun on(event: AccountUpdatedEvent) = runBlocking {
        accountViewRepo.findById(UUID.fromString(event.id))?.let {
            accountViewRepo.save(it.copy(name = event.name))
        }
        queryUpdateEmitter.emit(String::class.java, { it == event.id }, event)
    }

    @EventHandler
    fun on(event: AccountDeletedEvent) = runBlocking {
        accountViewRepo.deleteById(UUID.fromString(event.id))
        queryUpdateEmitter.emit(String::class.java, { it == event.id }, event)
    }
}
