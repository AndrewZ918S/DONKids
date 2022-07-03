package ru.donkids.mobile.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.donkids.mobile.data.local.CarouselDatabase
import ru.donkids.mobile.data.local.CatalogDatabase
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.StringToIntAdapter
import ru.donkids.mobile.data.repository.CatalogRepositoryImpl
import ru.donkids.mobile.data.repository.HomeRepositoryImpl
import ru.donkids.mobile.data.repository.LoginRepositoryImpl
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.domain.repository.LoginRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDonKidsApi(): DonKidsApi {
        return Retrofit.Builder()
            .baseUrl(DonKidsApi.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(StringToIntAdapter())
                        .build()
                )
            )
            .build()
            .create(DonKidsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(api: DonKidsApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(api: DonKidsApi, db: CatalogDatabase): CatalogRepository {
        return CatalogRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(db: CarouselDatabase): HomeRepository {
        return HomeRepositoryImpl(db)
    }

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
    fun provideCarouselDatabase(app: Application): CarouselDatabase {
        return Room.databaseBuilder(
            app,
            CarouselDatabase::class.java,
            "carousel.db"
        ).build()
    }
}
