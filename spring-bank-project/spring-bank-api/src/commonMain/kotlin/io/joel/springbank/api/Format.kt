package io.joel.springbank.api

import io.joel.springbank.api.request.*
import io.joel.springbank.api.response.AccountCreatedResponse
import io.joel.springbank.api.response.AccountDeletedResponse
import io.joel.springbank.api.response.AccountUpdatedResponse
import kotlinx.serialization.*
import kotlinx.serialization.modules.*
import kotlinx.serialization.protobuf.ProtoBuf

object Format {
    fun toProtobuf(obj: Message): ByteArray = format.encodeToByteArray(PolymorphicSerializer(Message::class), obj)

    fun fromProtobuf(arr: ByteArray): Message = format.decodeFromByteArray(PolymorphicSerializer(Message::class), arr)

    private val format = ProtoBuf {
        serializersModule = SerializersModule {
            polymorphic(Message::class) {
                // Requests
                subclass(CreateAccountRequest::class)
                subclass(DeleteAccountRequest::class)
                subclass(EchoRequest::class)
                subclass(NullRequest::class)
                subclass(UnitRequest::class)
                subclass(UpdateAccountRequest::class)
                // Responses
                subclass(AccountCreatedResponse::class)
                subclass(AccountDeletedResponse::class)
                subclass(AccountUpdatedResponse::class)
            }
        }
    }
}
