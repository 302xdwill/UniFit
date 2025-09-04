package com.example.unifit.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unifit.domain.usecase.user.GetUserByIdUseCase
import com.example.unifit.domain.usecase.user.LoginUserUseCase
import com.example.unifit.domain.usecase.user.RegisterUserUseCase
import com.example.unifit.domain.usecase.user.LogoutUserUseCase
import com.example.unifit.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val userId: Long) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val sessionManager: SessionManager,
    getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    /**
     * 🔑 Iniciar sesión
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val user = loginUserUseCase(email, password)
                if (user != null) {
                    sessionManager.saveUserId(user.id) // guardar sesión
                    _state.value = AuthState.Success(user.id)
                } else {
                    _state.value = AuthState.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }

    /**
     * 📝 Registrar usuario
     */
    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val userId = registerUserUseCase(username, email, password)
                sessionManager.saveUserId(userId) // guardar sesión
                _state.value = AuthState.Success(userId)
            } catch (e: Exception) {
                _state.value = AuthState.Error("Error al registrar: ${e.message}")
            }
        }
    }

    /**
     * 🚪 Cerrar sesión
     */
    fun logout() {
        viewModelScope.launch {
            try {
                logoutUserUseCase()
                _state.value = AuthState.Idle
            } catch (e: Exception) {
                _state.value = AuthState.Error("Error al cerrar sesión")
            }
        }
    }

    /**
     * 🔄 Resetear estado manualmente
     */
    fun clearState() {
        _state.value = AuthState.Idle
    }
}
