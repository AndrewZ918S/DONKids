package ru.donkids.mobile.data.remote.response

import com.squareup.moshi.Json
import ru.donkids.mobile.data.remote.annotations.ListObject
import ru.donkids.mobile.data.remote.dto.ProductDto
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.data.remote.enums.Status

data class ProductResponse(
    @Json(name = "KVO") val amount: String?,
    @Json(name = "TOVAR") @ListObject val products: List<ProductDto>?,
    @Json(name = "status") val status: Status,
    @Json(name = "error") val error: Error?,
    @Json(name = "link") val link: String?
)
