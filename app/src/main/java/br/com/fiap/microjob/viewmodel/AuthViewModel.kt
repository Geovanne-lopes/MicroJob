package br.com.fiap.microjob.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.microjob.model.User
import br.com.fiap.microjob.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthState(
    val loginError: String? = null,
    val registerError: String? = null
)

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getCurrentUserFlow().collect { user ->
                _currentUser.value = user
            }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _authState.update { it.copy(loginError = null) }
            val user = userRepository.login(email, password)
            if (user != null) {
                _currentUser.value = user
                _authState.update { it.copy(loginError = null) }
                onResult(true, null)
            } else {
                _authState.update { it.copy(loginError = "Email ou senha incorretos") }
                onResult(false, "Email ou senha incorretos")
            }
        }
    }

    fun register(name: String, email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            _authState.update { it.copy(registerError = null) }
            userRepository.register(name, email, password)
                .onSuccess { user ->
                    _currentUser.value = user
                    _authState.update { it.copy(registerError = null) }
                    onResult(true, null)
                }
                .onFailure { e ->
                    val msg = e.message ?: "Erro ao cadastrar"
                    _authState.update { it.copy(registerError = msg) }
                    onResult(false, msg)
                }
        }
    }

    fun logout() {
        userRepository.logout()
        _currentUser.value = null
        _authState.update { AuthState() }
    }

    fun clearLoginError() {
        _authState.update { it.copy(loginError = null) }
    }

    fun clearRegisterError() {
        _authState.update { it.copy(registerError = null) }
    }

    fun onJobPosted(userId: String) {
        viewModelScope.launch {
            userId.toLongOrNull()?.let { userRepository.incrementJobsPosted(it) }
        }
    }
}
