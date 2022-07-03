package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json

data class ProductRequest(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "prc") val type: String = "T",
    @field:Json(name = "mod") val options: String = "ALL",
    @field:Json(name = "off") val offset: String = "",
    @field:Json(name = "lim") val limit: String = "",
    @field:Json(name = "poid") val ids: String = "",
    @field:Json(name = "param") val misc: String = "",
)

