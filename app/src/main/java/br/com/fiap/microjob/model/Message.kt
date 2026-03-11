package br.com.fiap.microjob.model

/**
 * Representa uma mensagem no chat.
 */
data class Message(
    val id: String,
    val text: String,
    val isFromCurrentUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
