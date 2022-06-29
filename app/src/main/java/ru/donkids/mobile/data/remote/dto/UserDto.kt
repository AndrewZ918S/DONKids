package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json

data class UserDto(
    @field:Json(name = "hl") val apiKey: String,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "error") val errorType: String,
    @field:Json(name = "error-text") val errorText: String,
    @field:Json(name = "user") val username: String,
    @field:Json(name = "*") val misc: String
)
