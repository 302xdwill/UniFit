package com.example.unifit.domain.repository

import com.example.unifit.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User): Long
    suspend fun getUserById(id: Long): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
}
