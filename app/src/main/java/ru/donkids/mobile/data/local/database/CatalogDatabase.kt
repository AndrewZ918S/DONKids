package ru.donkids.mobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.donkids.mobile.data.local.dao.CatalogDao
import ru.donkids.mobile.data.local.entities.ProductEntity
import ru.donkids.mobile.data.mapper.toProduct
import ru.donkids.mobile.data.mapper.toProductEntity
import ru.donkids.mobile.domain.model.Product

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class CatalogDatabase : RoomDatabase() {
    abstract val dao: CatalogDao

    suspend fun updateProduct(product: Product) {
        dao.updateProduct(product.toProductEntity())
    }

    suspend fun updateCatalog(products: List<Product>) {
        if (products.isEmpty())
            return

        dao.clearProducts()
        dao.insertProducts(products.map { it.toProductEntity() })
    }

    suspend fun getCatalog(): List<Product> {
        return dao.getProducts().map { it.toProduct() }
    }

    suspend fun updateCategories(categories: List<Product>) {
        if (categories.isEmpty())
            return

        dao.clearCategories()
        dao.insertProducts(categories.map { it.toProductEntity() })
    }

    suspend fun getCategories(): List<Product> {
        return dao.getCategories().map { it.toProduct() }
    }

    suspend fun getChildProducts(parentId: Int): List<Product> {
        return dao.getChildProducts(parentId).map { it.toProduct() }
    }

    suspend fun getProductWithId(id: Int): Product? {
        val entities = dao.getProductsWithId(id)
        return getSingleProduct(entities)
    }

    suspend fun getProductWithCode(code: String): Product? {
        val entities = dao.getProductsWithCode(code)
        return getSingleProduct(entities)
    }

    suspend fun getProductWithBarcode(barcode: String): Product? {
        val entities = dao.getProductsWithBarcode(barcode)
        return getSingleProduct(entities)
    }

    private fun getSingleProduct(entities: List<ProductEntity>): Product? {
        val products = entities.map { entity ->
            entity.toProduct()
        }
        return if (products.isNotEmpty()) {
            products[0]
        } else {
            null
        }
    }
}