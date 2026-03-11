package br.com.fiap.microjob.model

/**
 * Representa um usuário da plataforma MicroJob.
 */
data class User(
    val id: String,
    val name: String,
    val community: String,
    val jobsPostedCount: Int = 0
)
