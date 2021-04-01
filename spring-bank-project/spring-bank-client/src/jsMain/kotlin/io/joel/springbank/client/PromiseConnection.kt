
package io.joel.springbank.client

import io.joel.springbank.api.Message
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.js.Promise

@JsExport
class PromiseConnection private constructor(private val delegate: Connection) {
    @JsName("send")
    fun send(msg: Message): Promise<*> = GlobalScope.promise {
        delegate.send(msg)
    }

    @JsName("receive")
    fun receive() = GlobalScope.promise {
        delegate.receive()
    }

    @JsName("close")
    fun close(): Promise<*> = GlobalScope.promise {
        delegate.close()
    }

    companion object {
        fun open(url: String) = GlobalScope.promise {
            PromiseConnection(Connection.open(url))
        }
    }
}

@JsExport
fun connect(url: String) = PromiseConnection.open(url)
