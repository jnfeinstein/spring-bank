package io.joel.springbank.client

import io.joel.springbank.api.Format
import io.joel.springbank.api.Message
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*

public class Connection private constructor(private val session: WebSocketSession) {
    public suspend fun send(msg: Message) {
        val data = Frame.Binary(true, Format.toProtobuf(msg))
        session.send(data)
    }

    public suspend fun receive(): Message {
        val data = session.incoming.receive().data
        return Format.fromProtobuf(data)
    }

    public suspend fun close() {
        session.close()
    }

    public companion object {
        public suspend fun open(url: String): Connection {
            return Connection(
                client.webSocketSession {
                    url(url)
                }
            )
        }

        private val client by lazy {
            HttpClient {
                install(WebSockets)
            }
        }
    }
}
