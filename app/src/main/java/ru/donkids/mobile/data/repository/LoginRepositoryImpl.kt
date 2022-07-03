package ru.donkids.mobile.data.repository

import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.dto.UserDto
import ru.donkids.mobile.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: DonKidsApi
) : LoginRepository {
    override suspend fun getApiKey(): String {
        return api.getKey().key
    }

    override suspend fun loginUser(key: String, id: String): UserDto {
        return api.loginUser(key, id)
    }

    override suspend fun updateUser(key: String, id: String): UserDto {
        return api.updateUser(key, id)
    }
}
