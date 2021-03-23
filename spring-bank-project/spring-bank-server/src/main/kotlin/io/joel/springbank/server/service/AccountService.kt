package io.joel.springbank.server.service

import io.joel.springbank.api.event.AccountCreatedEvent
import io.joel.springbank.api.event.AccountDeletedEvent
import io.joel.springbank.api.event.AccountUpdatedEvent
import io.joel.springbank.server.db.AccountView
import io.joel.springbank.server.db.AccountViewRepo
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
            AccountView(id = event.id, name = event.name)
        ).awaitSingle()
        queryUpdateEmitter.emit(UUID::class.java, { it == event.id }, event)
    }

    @EventHandler
    fun on(event: AccountUpdatedEvent) = runBlocking {
        accountViewRepo.findById(event.id)?.let {
            accountViewRepo.save(it.copy(name = event.name))
        }
        queryUpdateEmitter.emit(UUID::class.java, { it == event.id }, event)
    }

    @EventHandler
    fun on(event: AccountDeletedEvent) = runBlocking {
        accountViewRepo.deleteById(event.id)
    }
}
