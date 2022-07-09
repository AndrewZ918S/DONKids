package ru.donkids.mobile.data.local.dao

import androidx.room.*
import ru.donkids.mobile.data.local.entities.BannerEntity
import ru.donkids.mobile.data.local.entities.RecentEntity

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanners(banners: List<BannerEntity>)

    @Query("DELETE FROM BannerEntity")
    suspend fun clearBanners()

    @Query("SELECT * FROM BannerEntity")
    suspend fun getBanners(): List<BannerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecent(recent: RecentEntity)

    @Delete
    suspend fun deleteRecent(recent: RecentEntity)

    @Update
    suspend fun updateRecent(recent: RecentEntity)

    @Query("DELETE FROM RecentEntity")
    suspend fun clearHistory()

    @Query("SELECT * FROM RecentEntity ORDER BY timestamp DESC")
    suspend fun getHistory(): List<RecentEntity>
}
