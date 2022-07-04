package ru.donkids.mobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.donkids.mobile.data.local.dao.CarouselDao
import ru.donkids.mobile.data.local.entities.BannerEntity

@Database(
    entities = [BannerEntity::class],
    version = 1
)
abstract class CarouselDatabase : RoomDatabase() {
    abstract val dao: CarouselDao
}