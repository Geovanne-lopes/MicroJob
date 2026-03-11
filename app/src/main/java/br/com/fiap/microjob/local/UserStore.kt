package br.com.fiap.microjob.local

import android.content.Context
import android.content.SharedPreferences
import br.com.fiap.microjob.local.entity.UserEntity
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest

/**
 * Armazenamento local de usuários em SharedPreferences (JSON).
 * Evita dependência de Room/KSP para o MVP.
 */
class UserStore(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("microjob_users", Context.MODE_PRIVATE)
    private val keyUsers = "users_json"
    private val keyCurrentId = "current_user_id"
    private val keySeeded = "test_user_seeded"

    private val users = mutableListOf<UserEntity>()
    private var nextId = 1L

    init {
        load()
        ensureTestUser()
    }

    private fun load() {
        users.clear()
        val json = prefs.getString(keyUsers, "[]") ?: "[]"
        try {
            val arr = JSONArray(json)
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                users.add(
                    UserEntity(
                        id = obj.getLong("id"),
                        name = obj.getString("name"),
                        email = obj.getString("email"),
                        passwordHash = obj.getString("passwordHash"),
                        community = obj.optString("community", "Comunidade"),
                        jobsPostedCount = obj.optInt("jobsPostedCount", 0)
                    )
                )
            }
            nextId = (users.maxOfOrNull { it.id } ?: 0L) + 1L
        } catch (_: Exception) { }
    }

    private fun save() {
        val arr = JSONArray()
        users.forEach { u ->
            arr.put(JSONObject().apply {
                put("id", u.id)
                put("name", u.name)
                put("email", u.email)
                put("passwordHash", u.passwordHash)
                put("community", u.community)
                put("jobsPostedCount", u.jobsPostedCount)
            })
        }
        prefs.edit().putString(keyUsers, arr.toString()).apply()
    }

    private fun ensureTestUser() {
        if (prefs.getBoolean(keySeeded, false)) return
        if (users.any { it.email == "teste@teste.com" }) {
            prefs.edit().putBoolean(keySeeded, true).apply()
            return
        }
        insert(UserEntity(
            name = "Teste",
            email = "teste@teste.com",
            passwordHash = hashPassword("teste123"),
            community = "Vila Esperança",
            jobsPostedCount = 0
        ))
        prefs.edit().putBoolean(keySeeded, true).apply()
    }

    fun getCurrentUserId(): Long? {
        val id = prefs.getLong(keyCurrentId, -1L)
        return if (id >= 0) id else null
    }

    fun setCurrentUserId(id: Long?) {
        prefs.edit().putLong(keyCurrentId, id ?: -1L).apply()
    }

    fun getByEmail(email: String): UserEntity? = users.find { it.email.equals(email.trim(), ignoreCase = true) }

    fun getById(id: Long): UserEntity? = users.find { it.id == id }

    fun insert(user: UserEntity): Long {
        val newUser = user.copy(id = nextId++)
        users.add(newUser)
        save()
        return newUser.id
    }

    fun update(user: UserEntity) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index >= 0) {
            users[index] = user
            save()
        }
    }

    fun countByEmail(email: String): Int = users.count { it.email.equals(email.trim(), ignoreCase = true) }

    companion object {
        fun hashPassword(password: String): String {
            val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray(Charsets.UTF_8))
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
