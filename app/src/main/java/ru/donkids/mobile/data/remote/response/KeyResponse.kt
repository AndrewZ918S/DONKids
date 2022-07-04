package ru.donkids.mobile.data.remote.response

import com.squareup.moshi.Json

data class KeyResponse(
    @Json(name = "hl") val key: String
)
