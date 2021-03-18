package io.joel.springbank.api.aggregate

import io.joel.springbank.domain.command.account.CreateAccountCommand
import io.joel.springbank.domain.command.account.DeleteAccountCommand
import io.joel.springbank.domain.command.account.UpdateAccountCommand
import io.joel.springbank.domain.event.account.AccountCreatedEvent
import io.joel.springbank.domain.event.account.AccountDeletedEvent
import io.joel.springbank.domain.event.account.AccountUpdatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

@Aggregate
class Account() {
    @AggregateIdentifier
    private lateinit var id: UUID

    @CommandHandler
    constructor(command: CreateAccountCommand) : this() {
        AggregateLifecycle.apply(
            AccountCreatedEvent(id = command.id, name = command.name)
        )
    }

    @EventSourcingHandler
    fun on(event: AccountCreatedEvent) {
        id = event.id
    }

    @CommandHandler
    fun handle(command: UpdateAccountCommand) {
        AggregateLifecycle.apply(
            AccountUpdatedEvent(id = id, name = command.name)
        )
    }

    @CommandHandler
    fun handle(command: DeleteAccountCommand) {
        AggregateLifecycle.apply(
            AccountDeletedEvent(id = id)
        )
    }

    @EventSourcingHandler
    fun on(event: AccountDeletedEvent) {
        AggregateLifecycle.markDeleted()
    }
}
