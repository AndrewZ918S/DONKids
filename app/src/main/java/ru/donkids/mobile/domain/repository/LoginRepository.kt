package ru.donkids.mobile.domain.repository

import ru.donkids.mobile.data.remote.dto.UserDto

interface LoginRepository {
    suspend fun getApiKey(): String

    suspend fun loginUser(key: String, id: String): UserDto

    suspend fun updateUser(key: String, id: String): UserDto
}