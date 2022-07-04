package ru.donkids.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.donkids.mobile.data.local.entities.ProductEntity

@Dao
interface CatalogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(
        productEntities: List<ProductEntity>
    )

    @Query("DELETE FROM productentity")
    suspend fun clearProducts()

    @Query("SELECT * FROM productentity WHERE :parentId == parentId")
    suspend fun getChildProducts(parentId: Int): List<ProductEntity>

    @Query("SELECT * FROM productentity WHERE :id == id")
    suspend fun getProductsWithId(id: Int): List<ProductEntity>
}