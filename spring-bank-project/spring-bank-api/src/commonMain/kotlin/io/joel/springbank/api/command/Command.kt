package io.joel.springbank.api.command

import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.modules.subclass

internal object Command {
    fun registerSubclasses(provider: PolymorphicModuleBuilder<Any>) = with(provider) {
        subclass(CreateAccountCommand::class)
        subclass(DeleteAccountCommand::class)
        subclass(EchoCommand::class)
        subclass(NullCommand::class)
        subclass(UpdateAccountCommand::class)
    }
}
