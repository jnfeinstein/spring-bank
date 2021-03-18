package io.joel.springbank.api.controller

import io.joel.springbank.api.db.AccountView
import io.joel.springbank.api.db.AccountViewRepo
import io.joel.springbank.domain.command.account.CreateAccountCommand
import io.joel.springbank.domain.command.account.DeleteAccountCommand
import io.joel.springbank.domain.command.account.UpdateAccountCommand
import io.joel.springbank.dto.CreateAccountRequest
import io.joel.springbank.dto.UpdateAccountRequest
import kotlinx.coroutines.flow.Flow
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val commandGateway: CommandGateway,
    private val accountViewRepo: AccountViewRepo,
) {
    @GetMapping
    suspend fun getAccounts(): Flow<AccountView> {
        return accountViewRepo.findAll()
    }

    @GetMapping("/{id}")
    suspend fun getAccount(@PathVariable id: UUID): ResponseEntity<AccountView> {
        return accountViewRepo.findById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/search")
    suspend fun getAccountsByName(@RequestParam name: String): Flow<AccountView> {
        return accountViewRepo.findByNameContains(name)
    }

    @PostMapping
    suspend fun createAccount(@RequestBody request: CreateAccountRequest) {
        commandGateway.sendAndWait<Unit>(
            CreateAccountCommand(id = UUID.randomUUID(), name = request.name)
        )
    }

    @PutMapping
    suspend fun updateAccount(@RequestBody request: UpdateAccountRequest) {
        commandGateway.sendAndWait<Unit>(
            UpdateAccountCommand(id = UUID.fromString(request.id), name = request.name)
        )
    }

    @DeleteMapping("/{id}")
    suspend fun deleteAccount(@PathVariable id: UUID) {
        commandGateway.sendAndWait<Unit>(
            DeleteAccountCommand(id)
        )
    }
}
