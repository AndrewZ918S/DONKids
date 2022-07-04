package ru.donkids.mobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.donkids.mobile.data.local.entities.BannerEntity

@Dao
interface CarouselDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanners(
        bannerEntities: List<BannerEntity>
    )

    @Query("DELETE FROM bannerentity")
    suspend fun clearBanners()

    @Query("SELECT * FROM bannerentity")
    suspend fun getBanners(): List<BannerEntity>
}