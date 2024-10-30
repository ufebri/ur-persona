package semicolon.dev.yourpersona.util.websocket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketManager(private val serverUrl: String) {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    // Fungsi untuk menghubungkan ke WebSocket
    fun connect() {
        val request = Request.Builder().url(serverUrl).build()
        webSocket = client.newWebSocket(request, socketListener)
    }

    // Fungsi untuk mengirim pesan ke server WebSocket
    fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    // Fungsi untuk memutuskan koneksi
    fun disconnect() {
        webSocket?.close(1000, null)
    }

    // Listener untuk menangani event WebSocket
    private val socketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            Log.d("WebSocket", "Connected to server")
            webSocket.send("Hello from Android!")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("WebSocket", "Received: $text")
            // Tampilkan notifikasi atau update UI sesuai pesan
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.d("WebSocket", "Received bytes: ${bytes.hex()}")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("WebSocket", "Closing: $reason")
            webSocket.close(1000, null)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            Log.e("WebSocket", "Error: ${t.message}")
        }
    }
}
