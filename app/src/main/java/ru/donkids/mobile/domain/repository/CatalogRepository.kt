package ru.donkids.mobile.domain.repository

import ru.donkids.mobile.domain.model.Product

interface CatalogRepository {
    suspend fun getCategories(): List<Product>

    suspend fun getProducts(): List<Product>
}