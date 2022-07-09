package ru.donkids.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BannerEntity(
    val page: String,
    @PrimaryKey val image: String,
    val code: String? = null
)
