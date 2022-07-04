package ru.donkids.mobile.domain.repository

import ru.donkids.mobile.data.remote.response.LoginResponse
import ru.donkids.mobile.domain.model.User

interface LoginRepository {
    suspend fun getApiKey(): String

    suspend fun loginUser(key: String, id: String): LoginResponse

    suspend fun updateUser(key: String, id: String): LoginResponse

    suspend fun checkUser(user: User): Boolean
}