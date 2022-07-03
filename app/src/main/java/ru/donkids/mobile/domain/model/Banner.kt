package ru.donkids.mobile.domain.model

data class Banner(
    val pageLink: String,
    val imageLink: String,
    val vendorCode: String? = null
)
