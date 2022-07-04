package ru.donkids.mobile.data.remote.request

import com.squareup.moshi.Json

data class ProductRequest(
    @Json(name = "id") val id: String,
    @Json(name = "prc") val type: String = "T",
    @Json(name = "mod") val options: String = "ALL",
    @Json(name = "off") val offset: String? = null,
    @Json(name = "lim") val limit: String? = null,
    @Json(name = "poid") val ids: String? = null,
    @Json(name = "param") val misc: String? = null,
)

