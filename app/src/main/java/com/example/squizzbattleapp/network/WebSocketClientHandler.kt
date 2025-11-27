package com.example.squizzbattleapp.network

import android.util.Log

import okhttp3.*
import okio.ByteString


class WebSocketClientHandler(
    private val serverUrl:String= "ws://10.0.2.2:8080/ws/game",
    private val listener: GameWebSocketListener
) {
    private var webSocket:WebSocket?= null
    private val client= OkHttpClient()

    fun connect(){
        val request= Request.Builder()
            .url(serverUrl)
            .build()

        webSocket= client.newWebSocket(request, object: WebSocketListener() {
            override fun onOpen(webSocket:WebSocket, response:Response){
                Log.d("WebSocket", "Connection established")
                listener.onConnected()
            }

            override fun onMessage(webSocket: WebSocket, text:String) {
                Log.d("Websocket", "Message received: $text")
                listener.onMessageReceived(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("WebSocket", "Binary message received: $bytes")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("Websocket", "Connection closing:  $reason")
                listener.onDisConnected()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("Websocket", "Error: ${t.message}")
                listener.onError(t.message?: "unknown error")
            }

        })

        fun sendMessage (message:String){
            webSocket?.send(message)
            Log.d("WebSocket", "Send message: $message")
        }

        fun disconnect() {
            webSocket?.close(1000,"App closed")
            webSocket= null
        }

    }

}