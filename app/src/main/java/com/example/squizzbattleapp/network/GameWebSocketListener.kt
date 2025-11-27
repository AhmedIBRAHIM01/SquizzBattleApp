package com.example.squizzbattleapp.network

interface GameWebSocketListener {
    fun onConnected()
    fun onMessageReceived(message:String)
    fun onDisConnected()
    fun onError(error: String)
}