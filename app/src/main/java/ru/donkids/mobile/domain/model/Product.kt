package ru.donkids.mobile.domain.model

import ru.donkids.mobile.data.remote.DonKidsApi

data class Product(
    val id: Int,
    val code: String,
    val vendorCode: String,
    val price: Float?,
    val isCategory: Boolean,
    val properties: String,
    val imageHash: String,
    val imagePath: String,
    val keywords: String,
    val abbreviation: String,
    val title: String,
    val isAvailable: Boolean,
    val size: String,
    val parentId: Int,
    val barcode: String?,
    val updateIndex: String,
) {
    fun getImageLink(): String {
        return DonKidsApi.SITE_URL + imagePath
    }

    fun getThumbLink(): String {
        return DonKidsApi.BASE_URL + imagePath
    }
}
