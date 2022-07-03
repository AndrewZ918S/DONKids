package ru.donkids.mobile.data.remote.dto

import com.squareup.moshi.Json
import ru.donkids.mobile.data.remote.StringValue
import ru.donkids.mobile.domain.model.Product

data class ProductDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "art")
    val code: String,
    @field:Json(name = "artpost")
    val vendorCode: String,
    @field:StringValue
    @field:Json(name = "cena")
    val price: Float,
    @field:Json(name = "desc")
    val description: String,
    @field:StringValue
    @field:Json(name = "grp")
    val isCategory: Boolean,
    @field:Json(name = "har")
    val properties: String,
    @field:Json(name = "image")
    val imagePath: String,
    @field:Json(name = "image_hash")
    val imageHash: String,
    @field:Json(name = "image_link")
    val imageLink: String,
    @field:Json(name = "keywords")
    val keywords: String,
    @field:Json(name = "nazv")
    val abbreviation: String,
    @field:Json(name = "nazvpoln")
    val title: String,
    @field:StringValue
    @field:Json(name = "ost")
    val isAvailable: Boolean,
    @field:Json(name = "razmer")
    val size: String,
    @field:Json(name = "rod")
    val parentId: String,
    @field:Json(name = "shk")
    val barcode: String,
    @field:Json(name = "upd")
    val updateIndex: String
)
