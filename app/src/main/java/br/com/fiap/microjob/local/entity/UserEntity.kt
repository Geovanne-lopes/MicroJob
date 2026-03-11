package br.com.fiap.microjob.local.entity

/**
 * Representação de usuário no armazenamento local.
 */
data class UserEntity(
    val id: Long = 0,
    val name: String,
    val email: String,
    val passwordHash: String,
    val community: String = "Comunidade",
    val jobsPostedCount: Int = 0
)
