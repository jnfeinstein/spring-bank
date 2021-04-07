
package io.joel.springbank.client

import io.joel.springbank.api.Message
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.js.Promise

@JsExport
public class PromiseConnection private constructor(private val delegate: Connection) {
    @JsName("send")
    public fun send(msg: Message): Promise<*> = GlobalScope.promise {
        delegate.send(msg)
    }

    @JsName("receive")
    public fun receive(): Promise<Message> = GlobalScope.promise {
        delegate.receive()
    }

    @JsName("close")
    public fun close(): Promise<*> = GlobalScope.promise {
        delegate.close()
    }

    public companion object {
        public fun open(url: String): Promise<PromiseConnection> = GlobalScope.promise {
            PromiseConnection(Connection.open(url))
        }
    }
}

@JsExport
public fun connect(url: String): Promise<PromiseConnection> = PromiseConnection.open(url)
