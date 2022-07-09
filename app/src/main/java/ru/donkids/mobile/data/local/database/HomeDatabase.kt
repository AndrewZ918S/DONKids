package ru.donkids.mobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.donkids.mobile.data.local.dao.HomeDao
import ru.donkids.mobile.data.local.entities.BannerEntity
import ru.donkids.mobile.data.local.entities.RecentEntity
import ru.donkids.mobile.data.mapper.toBanner
import ru.donkids.mobile.data.mapper.toBannerEntity
import ru.donkids.mobile.data.mapper.toRecent
import ru.donkids.mobile.data.mapper.toRecentEntity
import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent

@Database(
    entities = [
        BannerEntity::class,
        RecentEntity::class
    ],
    version = 1
)
abstract class HomeDatabase : RoomDatabase() {
    abstract val dao: HomeDao

    suspend fun updateBanners(banners: List<Banner>) {
        if (banners.isEmpty())
            return

        dao.clearBanners()
        dao.insertBanners(banners.map { it.toBannerEntity() })
    }

    suspend fun getBanners(): List<Banner> {
        return dao.getBanners().map { it.toBanner() }
    }

    suspend fun updateHistory(recent: Recent) {
        if (dao.getHistory().any { it.id == recent.id }) {
            dao.updateRecent(recent.toRecentEntity())
        } else {
            dao.insertRecent(recent.toRecentEntity())
        }
    }

    suspend fun getHistory(): List<Recent> {
        return dao.getHistory().map { it.toRecent() }
    }

    suspend fun clearHistory() {
        return dao.clearHistory()
    }
}
