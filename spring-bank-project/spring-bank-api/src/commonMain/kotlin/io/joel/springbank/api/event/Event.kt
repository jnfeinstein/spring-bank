package io.joel.springbank.api.event

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.subclass

internal object Event {
    fun registerSubclasses(provider: PolymorphicModuleBuilder<Any>) = with(provider) {
        subclass(AccountCreatedEvent::class)
        subclass(AccountDeletedEvent::class)
        subclass(AccountUpdatedEvent::class)
    }
}
