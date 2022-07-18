package ru.donkids.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BannerEntity(
    val pagePath: String,
    @PrimaryKey val imagePath: String,
    val productCode: String? = null
)
