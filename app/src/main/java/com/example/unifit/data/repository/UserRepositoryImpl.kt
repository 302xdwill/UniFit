package com.example.unifit.data.repository

import com.example.unifit.data.local.dao.UserDao
import com.example.unifit.data.local.entity.UserEntity
import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User): Long {
        val entity = UserEntity(user.id, user.username, user.email, user.password)
        return userDao.insertUser(entity)
    }

    override suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id)?.let {
            User(it.id, it.username, it.email, it.password)
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.let {
            User(it.id, it.username, it.email, it.password)
        }
    }

    override suspend fun updateUser(user: User) {
        val entity = UserEntity(user.id, user.username, user.email, user.password)
        userDao.updateUser(entity)
    }

    override suspend fun deleteUser(user: User) {
        val entity = UserEntity(user.id, user.username, user.email, user.password)
        userDao.deleteUser(entity)
    }
}
