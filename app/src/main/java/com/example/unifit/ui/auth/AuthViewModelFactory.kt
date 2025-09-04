package com.example.unifit.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unifit.domain.usecase.user.GetUserByIdUseCase
import com.example.unifit.domain.usecase.user.LoginUserUseCase
import com.example.unifit.domain.usecase.user.LogoutUserUseCase
import com.example.unifit.domain.usecase.user.RegisterUserUseCase
import com.example.unifit.utils.SessionManager

class AuthViewModelFactory(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val sessionManager: SessionManager,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(
                loginUserUseCase = loginUserUseCase,
                registerUserUseCase = registerUserUseCase,
                logoutUserUseCase = logoutUserUseCase,
                sessionManager = sessionManager,
                getUserByIdUseCase = getUserByIdUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
