package io.joel.springbank.api

import io.joel.springbank.api.command.Command
import io.joel.springbank.api.event.Event
import kotlinx.serialization.*
import kotlinx.serialization.modules.*
import kotlinx.serialization.protobuf.ProtoBuf
import kotlin.js.JsExport

@JsExport
object Format {
    fun toProtobuf(obj: Any): ByteArray = format.encodeToByteArray(PolymorphicSerializer(Any::class), obj)

    fun fromProtobuf(arr: ByteArray): Any = format.decodeFromByteArray(PolymorphicSerializer(Any::class), arr)

    private val format = ProtoBuf {
        serializersModule = SerializersModule {
            polymorphic(Any::class) {
                Command.registerSubclasses(this)
                Event.registerSubclasses(this)
            }
        }
    }
}
