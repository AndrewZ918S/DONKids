package ru.donkids.mobile.domain.repository

import ru.donkids.mobile.data.remote.dto.ApiKeyDto
import ru.donkids.mobile.data.remote.dto.UserDto

interface LoginRepository {
    suspend fun getApiKey(): ApiKeyDto

    suspend fun signUp(key: String, id: String): UserDto

    suspend fun signIn(key: String, id: String): UserDto
}