package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json

data class ApiKeyDto(
    @field:Json(name = "hl") val key: String,
    @field:Json(name = "*") val misc: String
)