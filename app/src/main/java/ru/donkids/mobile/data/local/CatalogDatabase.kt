package ru.donkids.mobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.donkids.mobile.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class CatalogDatabase : RoomDatabase() {
    abstract val dao: CatalogDao
}