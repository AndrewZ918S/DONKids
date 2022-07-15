package ru.donkids.mobile.presentation.page_catalog

import ru.donkids.mobile.domain.model.Product

data class CatalogPageState(
    val destination: Product? = null,
    val categories: List<Product> = ArrayList()
)
