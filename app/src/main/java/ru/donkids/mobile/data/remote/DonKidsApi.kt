package ru.donkids.mobile.data.remote

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.donkids.mobile.data.remote.dto.ApiKeyDto
import ru.donkids.mobile.data.remote.dto.UserDto

interface DonKidsApi {
    companion object {
        const val BASE_URL = "http://pri.donkids.ru"
        const val LOGIN_PATH = "/login"
    }

    @GET(LOGIN_PATH)
    suspend fun getKey(): ApiKeyDto

    @POST(LOGIN_PATH)
    suspend fun signUp(@Query("r") key: String, @Query("h") id: String): UserDto

    @POST(LOGIN_PATH)
    suspend fun signIn(@Query("w") key: String, @Query("h") id: String): UserDto
}
