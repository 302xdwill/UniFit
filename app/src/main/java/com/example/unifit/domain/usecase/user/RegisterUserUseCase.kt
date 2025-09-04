package com.example.unifit.domain.usecase.user

import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository
import com.example.unifit.utils.PasswordUtils

class RegisterUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, email: String, password: String): Long {
        val hashed = PasswordUtils.hashPassword(password)
        val user = User(username = username, email = email, password = hashed)
        return userRepository.insertUser(user)
    }
}