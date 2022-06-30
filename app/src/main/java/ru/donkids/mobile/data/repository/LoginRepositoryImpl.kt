package ru.donkids.mobile.data.repository

import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.dto.ApiKeyDto
import ru.donkids.mobile.data.remote.dto.UserDto
import ru.donkids.mobile.domain.repository.LoginRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val api: DonKidsApi
) : LoginRepository {
    override suspend fun getApiKey(): ApiKeyDto {
        return api.getKey()
    }

    override suspend fun signUp(key: String, id: String): UserDto {
        return api.signUp(key, id)
    }

    override suspend fun signIn(key: String, id: String): UserDto {
        return api.signIn(key, id)
    }
}
