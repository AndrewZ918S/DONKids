package ru.donkids.mobile.domain.model

data class Recent(
    val id: Int,
    val code: String,
    val vendorCode: String,
    val price: Float?,
    val imageLink: String,
    val abbreviation: String,
    val isAvailable: Boolean,
    val timestamp: Long
)
