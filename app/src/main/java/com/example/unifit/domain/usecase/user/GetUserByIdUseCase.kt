package com.example.unifit.domain.usecase.user

import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: Long): Flow<User?> {
        return userRepository.getUserById(id)
    }
}
