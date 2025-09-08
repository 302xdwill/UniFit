package com.example.unifit.data.repository

import com.example.unifit.data.local.dao.UserDao
import com.example.unifit.data.local.entity.UserEntity
import com.example.unifit.domain.model.User
import com.example.unifit.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(user: User): Long {
        val entity = UserEntity(
            id = user.id,
            username = user.username,
            email = user.email,
            password = user.password
        )
        return userDao.insertUser(entity)
    }

    override suspend fun updateUser(user: User) {
        val entity = UserEntity(user.id, user.username, user.email, user.password)
        userDao.updateUser(entity)
    }

    override suspend fun deleteUser(user: User) {
        val entity = UserEntity(user.id, user.username, user.email, user.password)
        userDao.deleteUser(entity)
    }

    override suspend fun getUserById(id: Long): Flow<User?> {
        return userDao.getUserById(id).map { entity ->
            entity?.let {
                User(
                    id = it.id,
                    username = it.username,
                    email = it.email,
                    password = it.password
                )
            }
        }
    }


    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.let {
            User(it.id, it.username, it.email, it.password)
        }
    }
}
