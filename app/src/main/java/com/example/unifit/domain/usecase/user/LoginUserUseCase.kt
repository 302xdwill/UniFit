package com.example.unifit.domain.usecase.user

import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository
import com.example.unifit.utils.PasswordUtils

class LoginUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): User? {
        val user = userRepository.getUserByEmail(email) ?: return null
        return if (PasswordUtils.verifyPassword(password, user.password)) user else null
    }
}