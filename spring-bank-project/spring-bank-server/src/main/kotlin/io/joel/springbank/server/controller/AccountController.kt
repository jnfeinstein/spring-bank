package io.joel.springbank.server.controller

import io.joel.springbank.api.request.CreateAccountRequest
import io.joel.springbank.api.request.DeleteAccountRequest
import io.joel.springbank.api.request.UpdateAccountRequest
import io.joel.springbank.api.response.AccountCreatedResponse
import io.joel.springbank.api.response.AccountDeletedResponse
import io.joel.springbank.api.response.AccountUpdatedResponse
import io.joel.springbank.server.db.AccountView
import io.joel.springbank.server.db.AccountViewRepo
import io.joel.springbank.server.domain.command.CreateAccountCommand
import io.joel.springbank.server.domain.command.DeleteAccountCommand
import io.joel.springbank.server.domain.command.UpdateAccountCommand
import io.joel.springbank.server.domain.event.AccountCreatedEvent
import io.joel.springbank.server.domain.event.AccountDeletedEvent
import io.joel.springbank.server.domain.event.AccountUpdatedEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirst
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

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
    suspend fun getAccount(@PathVariable id: String): ResponseEntity<AccountView> {
        return accountViewRepo.findById(UUID.fromString(id))?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/search")
    suspend fun getAccountsByName(@RequestParam name: String): Flow<AccountView> {
        return accountViewRepo.findByNameContains(name)
    }

    @PostMapping
    suspend fun createAccount(@RequestBody request: CreateAccountRequest) = handle(request)

    @PutMapping
    suspend fun updateAccount(@RequestBody request: UpdateAccountRequest) = handle(request)

    @DeleteMapping("/{id}")
    suspend fun deleteAccount(@PathVariable id: String) = handle(DeleteAccountRequest(id))

    @WebSocketRoute
    suspend fun handle(request: CreateAccountRequest): AccountCreatedResponse {
        val command = CreateAccountCommand(request)
        return queryGateway.subscriptionQuery(
            command.id,
            ResponseTypes.instanceOf(Unit::class.java),
            ResponseTypes.instanceOf(AccountCreatedEvent::class.java)
        ).use {
            commandGateway.sendAndWait<Unit>(command)
            it.updates().awaitFirst()
        }.toResponse()
    }

    @WebSocketRoute
    suspend fun handle(request: UpdateAccountRequest): AccountUpdatedResponse {
        val command = UpdateAccountCommand(request)
        return queryGateway.subscriptionQuery(
            command.id,
            ResponseTypes.instanceOf(Unit::class.java),
            ResponseTypes.instanceOf(AccountUpdatedEvent::class.java)
        ).use {
            commandGateway.sendAndWait<Unit>(command)
            it.updates().awaitFirst()
        }.toResponse()
    }

    @WebSocketRoute
    suspend fun handle(request: DeleteAccountRequest): AccountDeletedResponse {
        val command = DeleteAccountCommand(request)
        return queryGateway.subscriptionQuery(
            command.id,
            ResponseTypes.instanceOf(Unit::class.java),
            ResponseTypes.instanceOf(AccountDeletedEvent::class.java)
        ).use {
            commandGateway.sendAndWait<Unit>(command)
            it.updates().awaitFirst()
        }.toResponse()
    }
}
