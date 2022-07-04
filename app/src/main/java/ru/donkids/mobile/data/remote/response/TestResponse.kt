package ru.donkids.mobile.data.remote.response

import com.squareup.moshi.Json
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.data.remote.enums.Status

data class TestResponse(
    @Json(name = "status") val status: Status?,
    @Json(name = "error") val error: Error?,
    @Json(name = "link") val link: String?
)
