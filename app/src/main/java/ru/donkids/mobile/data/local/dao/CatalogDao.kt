package ru.donkids.mobile.data.local.dao

import androidx.room.*
import ru.donkids.mobile.data.local.entities.ProductEntity

@Dao
interface CatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity")
    suspend fun getProducts(): List<ProductEntity>

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM ProductEntity")
    suspend fun clearProducts()

    @Query("DELETE FROM ProductEntity WHERE isCategory == 1")
    suspend fun clearCategories()

    @Query("SELECT * FROM ProductEntity WHERE isCategory == 1")
    suspend fun getCategories(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE :parentId == parentId")
    suspend fun getChildProducts(parentId: Int): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE :id == id")
    suspend fun getProductsWithId(id: Int): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE :code == code")
    suspend fun getProductsWithCode(code: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE :barcode == barcode")
    suspend fun getProductsWithBarcode(barcode: String): List<ProductEntity>
}
