package ru.donkids.mobile.data.remote.response

import com.squareup.moshi.Json
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.data.remote.enums.Status

data class LoginResponse(
    @Json(name = "hl") val apiKey: String,
    @Json(name = "user") val username: String?,
    @Json(name = "status") val status: Status,
    @Json(name = "error") val error: Error?
)
