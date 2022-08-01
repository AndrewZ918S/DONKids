package ru.donkids.mobile.domain.model

import ru.donkids.mobile.data.remote.DonKidsApi

data class Recent(
    val id: Int,
    val code: String,
    val vendorCode: String,
    val price: Float?,
    val imagePath: String,
    val title: String,
    val isAvailable: Boolean,
    val timestamp: Long
) {
    fun getImageLink(): String {
        return DonKidsApi.SITE_URL + imagePath
    }

    fun getThumbLink(): String {
        return DonKidsApi.BASE_URL + imagePath
    }
}
