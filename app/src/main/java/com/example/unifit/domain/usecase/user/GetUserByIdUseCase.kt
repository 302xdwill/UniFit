package com.example.unifit.domain.usecase.user

import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: Long): User? = userRepository.getUserById(userId)
}