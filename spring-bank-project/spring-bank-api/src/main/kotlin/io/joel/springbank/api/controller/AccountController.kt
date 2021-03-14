package io.joel.springbank.api.controller

import io.joel.springbank.api.db.Account
import io.joel.springbank.api.db.AccountRepo
import io.joel.springbank.dto.Request
import kotlinx.coroutines.flow.Flow
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountRepo: AccountRepo,
) {
    @GetMapping
    suspend fun getAccounts(): Flow<Account> {
        return accountRepo.findAll()
    }

    @GetMapping("/{id}")
    suspend fun getAccount(@PathVariable id: Long): ResponseEntity<Account> {
        return accountRepo.findById(id)?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/search")
    suspend fun getAccountsByName(@RequestParam name: String): Flow<Account> {
        return accountRepo.findByNameContains(name)
    }

    @PostMapping
    suspend fun createAccount(@RequestBody request: Request.CreateAccount): Account {
        return accountRepo.save(
            Account(name = request.name)
        )
    }

    @PutMapping
    suspend fun updateAccount(@RequestBody request: Request.UpdateAccount): ResponseEntity<Account> {
        return accountRepo.findById(request.id.toLong())?.let {
            it.name = request.name
            accountRepo.save(it)
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    suspend fun deleteAccount(@PathVariable id: Long) {
        accountRepo.deleteById(id)
    }
}
