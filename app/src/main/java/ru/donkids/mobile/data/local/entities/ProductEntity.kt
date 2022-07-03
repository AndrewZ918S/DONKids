package ru.donkids.mobile.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    val id: String,
    val code: String,
    val vendorCode: String,
    val price: Float,
    val description: String,
    val isCategory: Boolean,
    val properties: String,
    val imagePath: String,
    val imageHash: String,
    val imageLink: String,
    val keywords: String,
    val abbreviation: String,
    val title: String,
    val isAvailable: Boolean,
    val size: String,
    val parentId: String,
    val barcode: String,
    val updateIndex: String,
    @PrimaryKey val key: Int? = null
)
