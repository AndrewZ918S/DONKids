package ru.donkids.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BannerEntity(
    val pageLink: String,
    val imageLink: String,
    val vendorCode: String? = null,
    @PrimaryKey val key: Int? = null
)
