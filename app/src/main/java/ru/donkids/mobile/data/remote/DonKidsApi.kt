package ru.donkids.mobile.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.donkids.mobile.data.remote.dto.ApiKeyDto
import ru.donkids.mobile.data.remote.dto.ProductDto
import ru.donkids.mobile.data.remote.dto.ProductRequest
import ru.donkids.mobile.data.remote.dto.UserDto

interface DonKidsApi {
    companion object {
        const val BASE_URL = "http://pri.donkids.ru"
        const val SITE_URL = "http://donkids.ru"
        const val SIGNUP_PATH = "/register"
        const val RESTORE_PATH = "/passrestore"
        const val LOGIN_PATH = "/login"
    }

    @GET(LOGIN_PATH)
    suspend fun getKey(): ApiKeyDto

    @GET(LOGIN_PATH)
    suspend fun loginUser(@Query("r") key: String, @Query("h") id: String): UserDto

    @GET(LOGIN_PATH)
    suspend fun updateUser(@Query("w") key: String, @Query("h") id: String): UserDto

    @POST
    suspend fun getProducts(@Body request: ProductRequest): List<ProductDto>
}
