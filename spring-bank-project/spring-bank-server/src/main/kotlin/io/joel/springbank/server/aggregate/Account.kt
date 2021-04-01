package io.joel.springbank.server.aggregate

import io.joel.springbank.server.domain.command.CreateAccountCommand
import io.joel.springbank.server.domain.command.DeleteAccountCommand
import io.joel.springbank.server.domain.command.UpdateAccountCommand
import io.joel.springbank.server.domain.event.AccountCreatedEvent
import io.joel.springbank.server.domain.event.AccountDeletedEvent
import io.joel.springbank.server.domain.event.AccountUpdatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Account() {
    @AggregateIdentifier
    private lateinit var id: String

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
