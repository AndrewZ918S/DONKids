package ru.donkids.mobile.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.remote.adapters.EnumAdapter
import ru.donkids.mobile.data.remote.adapters.ImageAdapter
import ru.donkids.mobile.data.remote.adapters.ListAdapter
import ru.donkids.mobile.data.remote.adapters.StringAdapter
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDonKidsApi(): DonKidsApi {
        return Retrofit.Builder()
            .baseUrl(DonKidsApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .build()
            )
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .add(StringAdapter())
                        .add(ImageAdapter())
                        .add(ListAdapter())
                        .add(EnumAdapter())
                        .build()
                )
            )
            .build()
            .create(DonKidsApi::class.java)
    }
}
