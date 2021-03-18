package io.joel.springbank.api.service

import io.joel.springbank.api.db.AccountView
import io.joel.springbank.api.db.AccountViewRepo
import io.joel.springbank.domain.event.account.AccountCreatedEvent
import io.joel.springbank.domain.event.account.AccountDeletedEvent
import io.joel.springbank.domain.event.account.AccountUpdatedEvent
import kotlinx.coroutines.runBlocking
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountViewRepo: AccountViewRepo,
) {
    @EventHandler
    fun on(event: AccountCreatedEvent) = runBlocking {
        accountViewRepo.save(
            AccountView(id = event.id, name = event.name).apply { markNew() }
        )
    }

    @EventHandler
    fun on(event: AccountUpdatedEvent) = runBlocking {
        accountViewRepo.save(
            checkNotNull(accountViewRepo.findById(event.id)).copy(
                name = event.name
            )
        )
    }

    @EventHandler
    fun on(event: AccountDeletedEvent) = runBlocking {
        accountViewRepo.deleteById(event.id)
    }
}
