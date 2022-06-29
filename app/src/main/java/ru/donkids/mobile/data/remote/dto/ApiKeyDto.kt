package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json
import ru.donkids.mobile.domain.model.ApiKey

data class ApiKeyDto(
    @field:Transient val timestamp: Long = System.currentTimeMillis(),
    @field:Json(name = "hl") val key: String,
    @field:Json(name = "*") val misc: String
)

fun ApiKeyDto.toApiKey(): ApiKey {
    return ApiKey(
        key,
        timestamp
    )
}