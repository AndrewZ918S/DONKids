package ru.donkids.mobile.ui.screens.product.entity

data class ProductScreenState(
    val category: String = "",
    val imageLink: String = "",
    val title: String = "",
    val productCode: String = "",
    val vendorCode: String = "",
    val properties: Map<String, String> = HashMap(),
    val isAvailable: Boolean = false,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false,
    val inCart: Int = 1,
    val price: Float? = 0f
)
