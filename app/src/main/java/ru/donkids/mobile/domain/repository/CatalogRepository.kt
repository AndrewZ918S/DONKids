package ru.donkids.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.util.Resource

interface CatalogRepository {
    suspend fun updateCatalog(): Flow<Resource<Unit>>

    suspend fun getCategories(): Flow<Resource<List<Product>>>

    suspend fun getProductById(id: Int, update: Boolean = false): Flow<Resource<Product>>

    suspend fun getProductByCode(code: String, update: Boolean = false): Flow<Resource<Product>>
}