package ru.donkids.mobile.ui.screens.main.pages.catalog.entity

import ru.donkids.mobile.domain.model.Product

data class CatalogPageState(
    val destination: Product? = null,
    val categories: List<Product> = ArrayList(),
    val products: List<Product>? = null,
    val query: String? = null
)
