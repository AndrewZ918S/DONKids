package ru.donkids.mobile.data.local

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
    suspend fun clearProductEntities()

    @Query(
        """
            SELECT * 
            FROM productentity
            WHERE :query == parentId
        """
    )
    suspend fun withParentId(query: Int): List<ProductEntity>
}