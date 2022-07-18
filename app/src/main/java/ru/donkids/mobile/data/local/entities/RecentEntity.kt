package ru.donkids.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentEntity(
    @PrimaryKey val id: Int,
    val code: String,
    val vendorCode: String,
    val price: Float?,
    val imagePath: String,
    val abbreviation: String,
    val isAvailable: Boolean,
    val timestamp: Long
)
