package br.com.fiap.microjob.model

/**
 * Representa um micro job publicado na plataforma.
 */
data class Job(
    val id: String,
    val title: String,
    val description: String,
    val paymentValue: Double,
    val location: String,
    val contactMethod: String,
    val poster: User
)
