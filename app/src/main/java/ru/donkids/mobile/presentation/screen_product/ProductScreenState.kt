package ru.donkids.mobile.presentation.screen_product

import ru.donkids.mobile.domain.model.Product

data class ProductScreenState(
    val product: Product? = null,
    val category: Product? = null
)
