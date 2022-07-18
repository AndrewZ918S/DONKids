package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json
import ru.donkids.mobile.data.remote.annotations.ImageHash
import ru.donkids.mobile.data.remote.annotations.ImageLink
import ru.donkids.mobile.data.remote.annotations.StringValue

data class ProductDto(
    @Json(name = "id") @StringValue val id: Int,
    @Json(name = "art") val code: String,
    @Json(name = "artpost") val vendorCode: String,
    @Json(name = "cena") @StringValue val price: Float?,
    @Json(name = "desc") val properties: String,
    @Json(name = "grp") @StringValue val isCategory: Boolean,
    @Json(name = "image_hash") @ImageHash val imageHash: String,
    @Json(name = "image_link") @ImageLink val imagePath: String,
    @Json(name = "keywords") val keywords: String,
    @Json(name = "nazv") val abbreviation: String,
    @Json(name = "nazvpoln") val title: String,
    @Json(name = "ost") @StringValue val isAvailable: Boolean,
    @Json(name = "razmer") val size: String,
    @Json(name = "rod") @StringValue val parentId: Int,
    @Json(name = "shk") val barcode: String?,
    @Json(name = "upd") val updateIndex: String
)
