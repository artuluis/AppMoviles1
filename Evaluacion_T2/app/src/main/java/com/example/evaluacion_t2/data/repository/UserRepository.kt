package com.example.evaluacion_t2.data.repository

import com.example.evaluacion_t2.data.local.UserDao
import com.example.evaluacion_t2.data.local.UserEntity
import com.example.evaluacion_t2.data.remote.ApiService
import com.example.evaluacion_t2.data.remote.ApiUser
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao,
    private val apiService: ApiService
) {
    val localUsers: Flow<List<UserEntity>> = userDao.getAllUsers()

    suspend fun login(username: String, password: String): UserEntity? {
        return userDao.login(username, password)
    }

    suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    suspend fun fetchApiUsers(): List<ApiUser> {
        return apiService.getUsers()
    }
}