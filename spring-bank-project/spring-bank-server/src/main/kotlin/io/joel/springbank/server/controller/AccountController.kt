package io.joel.springbank.server.controller

import io.joel.springbank.api.ObjectId
import io.joel.springbank.api.command.CreateAccountCommand
import io.joel.springbank.api.command.DeleteAccountCommand
import io.joel.springbank.api.command.UpdateAccountCommand
import io.joel.springbank.api.event.AccountCreatedEvent
import io.joel.springbank.server.db.AccountView
import io.joel.springbank.server.db.AccountViewRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirst
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountViewRepo: AccountViewRepo,
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway,
) {
    @GetMapping
    suspend fun getAccounts(): Flow<AccountView> {
        return accountViewRepo.findAll()
    }

    @GetMapping("/{id}")
    suspend fun getAccount(@PathVariable id: ObjectId): ResponseEntity<AccountView> {
        return accountViewRepo.findById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/search")
    suspend fun getAccountsByName(@RequestParam name: String): Flow<AccountView> {
        return accountViewRepo.findByNameContains(name)
    }

    @PostMapping
    suspend fun createAccount(@RequestBody command: CreateAccountCommand) {
        commandGateway.sendAndWait<Unit>(command)
    }

    @PutMapping
    suspend fun updateAccount(@RequestBody command: UpdateAccountCommand) {
        commandGateway.sendAndWait<Unit>(command)
    }

    @DeleteMapping("/{id}")
    suspend fun deleteAccount(@PathVariable id: ObjectId) {
        commandGateway.sendAndWait<Unit>(DeleteAccountCommand(id))
    }

    @WebSocketRoute
    suspend fun handle(command: CreateAccountCommand): AccountCreatedEvent {
        return queryGateway.subscriptionQuery(
            command.id,
            ResponseTypes.instanceOf(Unit::class.java),
            ResponseTypes.instanceOf(AccountCreatedEvent::class.java)
        ).use {
            commandGateway.sendAndWait<Unit>(command)
            it.updates().awaitFirst()
        }
    }
}
