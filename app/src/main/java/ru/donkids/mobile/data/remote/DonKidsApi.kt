package ru.donkids.mobile.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.donkids.mobile.data.remote.request.ProductRequest
import ru.donkids.mobile.data.remote.response.KeyResponse
import ru.donkids.mobile.data.remote.response.LoginResponse
import ru.donkids.mobile.data.remote.response.ProductResponse
import ru.donkids.mobile.data.remote.response.TestResponse

interface DonKidsApi {
    companion object {
        const val BASE_URL = "http://pri.donkids.ru"
        const val SITE_URL = "http://donkids.ru"
        const val SIGNUP_PATH = "/register"
        const val RESTORE_PATH = "/passrestore"
        const val LOGIN_PATH = "/login"
    }

    @GET(LOGIN_PATH)
    suspend fun getKey(): KeyResponse

    @GET(LOGIN_PATH)
    suspend fun loginUser(@Query("r") key: String, @Query("h") id: String): LoginResponse

    @GET(LOGIN_PATH)
    suspend fun updateUser(@Query("w") key: String, @Query("h") id: String): LoginResponse

    @GET(".")
    suspend fun checkUser(@Query("id") id: String): TestResponse

    @POST(".")
    suspend fun getProducts(@Body request: ProductRequest): ProductResponse
}
