package ru.donkids.mobile.domain.model

import ru.donkids.mobile.data.remote.DonKidsApi

data class Banner(
    val pagePath: String,
    val imagePath: String,
    val productCode: String? = null
) {
    fun getPageLink(): String {
        return DonKidsApi.SITE_URL + pagePath
    }

    fun getImageLink(): String {
        return DonKidsApi.SITE_URL + imagePath
    }

    fun getThumbLink(): String {
        return DonKidsApi.BASE_URL + imagePath
    }
}
