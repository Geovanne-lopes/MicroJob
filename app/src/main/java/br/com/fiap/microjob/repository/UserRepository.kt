package br.com.fiap.microjob.repository

import android.content.Context
import br.com.fiap.microjob.local.UserStore
import br.com.fiap.microjob.local.entity.UserEntity
import br.com.fiap.microjob.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepository(context: Context) {

    private val store = UserStore(context)
    private val prefs = context.getSharedPreferences("microjob_auth", Context.MODE_PRIVATE)
    private val currentUserIdKey = "current_user_id"

    private val _currentUserId = kotlinx.coroutines.flow.MutableStateFlow<Long?>(
        prefs.getLong(currentUserIdKey, -1L).let { if (it >= 0) it else null }
    )

    private fun getCurrentUserId(): Long? = _currentUserId.value

    private fun setCurrentUserId(id: Long?) {
        prefs.edit().putLong(currentUserIdKey, id ?: -1L).apply()
        _currentUserId.value = id
    }

    fun getCurrentUserFlow(): Flow<User?> = _currentUserId.map { id ->
        id?.let { store.getById(it)?.toUser() }
    }

    suspend fun getCurrentUser(): User? = withContext(Dispatchers.IO) {
        getCurrentUserId()?.let { store.getById(it)?.toUser() }
    }

    suspend fun login(email: String, password: String): User? = withContext(Dispatchers.IO) {
        val hash = UserStore.hashPassword(password)
        val entity = store.getByEmail(email.trim()) ?: return@withContext null
        if (entity.passwordHash != hash) return@withContext null
        setCurrentUserId(entity.id)
        entity.toUser()
    }

    suspend fun register(name: String, email: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        val trimmedEmail = email.trim()
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) return@withContext Result.failure(IllegalArgumentException("Nome é obrigatório"))
        if (trimmedEmail.isBlank()) return@withContext Result.failure(IllegalArgumentException("Email é obrigatório"))
        if (password.isBlank()) return@withContext Result.failure(IllegalArgumentException("Senha é obrigatória"))
        if (store.countByEmail(trimmedEmail) > 0) {
            return@withContext Result.failure(IllegalArgumentException("Este email já está cadastrado"))
        }
        val entity = UserEntity(
            name = trimmedName,
            email = trimmedEmail,
            passwordHash = UserStore.hashPassword(password),
            community = "Comunidade"
        )
        val id = store.insert(entity)
        setCurrentUserId(id)
        val created = store.getById(id)!!
        Result.success(created.toUser())
    }

    fun logout() {
        setCurrentUserId(null)
    }

    suspend fun incrementJobsPosted(userId: Long) = withContext(Dispatchers.IO) {
        store.getById(userId)?.let { entity ->
            store.update(entity.copy(jobsPostedCount = entity.jobsPostedCount + 1))
            _currentUserId.value = _currentUserId.value
        }
    }

    private fun UserEntity.toUser() = User(
        id = id.toString(),
        name = name,
        community = community,
        jobsPostedCount = jobsPostedCount
    )
}
