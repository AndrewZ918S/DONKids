package ru.donkids.mobile.presentation.screen_product

data class ProductScreenState(
    val category: String = "",
    val imageLink: String = "",
    val title: String = "",
    val productCode: String = "",
    val vendorCode: String = "",
    val isAvailable: Boolean = false,
    val isFavorite: Boolean = true,
    val inCart: Boolean = true,
    val price: Float? = null
)
