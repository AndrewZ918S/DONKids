package ru.donkids.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.util.Resource

interface CatalogRepository {
    suspend fun getCatalog(update: Boolean = false): Flow<Resource<List<Product>>>

    suspend fun getCategories(update: Boolean = false): Flow<Resource<List<Product>>>

    suspend fun getChildProducts(parentId: Int, update: Boolean = false): Flow<Resource<List<Product>>>

    suspend fun getProductById(id: Int, update: Boolean = false): Flow<Resource<Product>>

    suspend fun getProductByCode(code: String, update: Boolean = false): Flow<Resource<Product>>

    suspend fun getProductWithBarcode(barcode: String, update: Boolean = false): Flow<Resource<Product>>
}