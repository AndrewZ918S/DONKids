package ru.donkids.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.util.Resource

interface CatalogRepository {
    suspend fun getCategories(): Flow<Resource<List<Product>>>

    suspend fun getProducts(): Flow<Resource<List<Product>>>
}