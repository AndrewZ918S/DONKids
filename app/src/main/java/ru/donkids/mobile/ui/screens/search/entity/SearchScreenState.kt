package ru.donkids.mobile.ui.screens.search.entity

import ru.donkids.mobile.domain.model.Product

data class SearchScreenState(
    val query: String = "",
    val products: List<Product> = ArrayList(),
    val categories: List<Product> = ArrayList(),
    val parents: List<Product> = ArrayList()
)
