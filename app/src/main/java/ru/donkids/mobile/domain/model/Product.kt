package ru.donkids.mobile.domain.model

data class Product(
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
)
