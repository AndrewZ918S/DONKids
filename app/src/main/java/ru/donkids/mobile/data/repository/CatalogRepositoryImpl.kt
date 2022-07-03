package ru.donkids.mobile.data.repository

import ru.donkids.mobile.data.local.CatalogDatabase
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.dto.ProductRequest
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.domain.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val api: DonKidsApi,
    private val db: CatalogDatabase
) : CatalogRepository {
    override suspend fun getCategories(): List<Product> {
        api.getProducts(
            ProductRequest(
            TODO("Not yet implemented")
        )
        )
    }

    override suspend fun getProducts(): List<Product> {
        TODO("Not yet implemented")
    }

}