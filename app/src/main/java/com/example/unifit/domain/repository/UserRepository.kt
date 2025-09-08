package com.example.unifit.domain.repository

import com.example.unifit.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User): Long
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUserById(id: Long): Flow<User?>
    suspend fun getUserByEmail(email: String): User?
}
