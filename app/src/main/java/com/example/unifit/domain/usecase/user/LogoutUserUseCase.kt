package com.example.unifit.domain.usecase.user

import com.example.unifit.utils.SessionManager

class LogoutUserUseCase(
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke() {
        sessionManager.clearSession()
    }
}
