package ru.donkids.mobile.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.donkids.mobile.data.local.database.CatalogDatabase
import ru.donkids.mobile.data.local.database.HomeDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCatalogDatabase(app: Application): CatalogDatabase {
        return Room.databaseBuilder(
            app,
            CatalogDatabase::class.java,
            "catalog.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHomeDatabase(app: Application): HomeDatabase {
        return Room.databaseBuilder(
            app,
            HomeDatabase::class.java,
            "home.db"
        ).build()
    }
}