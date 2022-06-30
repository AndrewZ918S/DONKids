package ru.donkids.mobile.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.StringToIntAdapter
import ru.donkids.mobile.data.repository.CatalogRepositoryImpl
import ru.donkids.mobile.data.repository.LoginRepositoryImpl
import ru.donkids.mobile.domain.repository.CatalogRepository
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
    fun provideCatalogRepository(api: DonKidsApi): CatalogRepository {
        return CatalogRepositoryImpl(api)
    }
}
