package ru.donkids.mobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.donkids.mobile.data.local.database.CatalogDatabase
import ru.donkids.mobile.data.local.database.HomeDatabase
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.data.repository.CatalogRepositoryImpl
import ru.donkids.mobile.data.repository.HomeRepositoryImpl
import ru.donkids.mobile.data.repository.LoginRepositoryImpl
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.domain.repository.LoginRepository
import ru.donkids.mobile.domain.use_case.HttpRequest
import ru.donkids.mobile.domain.use_case.localize.ServerError
import ru.donkids.mobile.domain.use_case.localize.StringResource
import ru.donkids.mobile.domain.use_case.login.LoginAuto
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(api: DonKidsApi): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(
        api: DonKidsApi,
        db: CatalogDatabase,
        httpRequest: HttpRequest,
        serverError: ServerError,
        stringResource: StringResource,
        loginAuto: LoginAuto
    ): CatalogRepository {
        return CatalogRepositoryImpl(
            api = api,
            db = db,
            httpRequest = httpRequest,
            serverError = serverError,
            stringResource = stringResource,
            loginAuto = loginAuto
        )
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        db: HomeDatabase,
        httpRequest: HttpRequest
    ): HomeRepository {
        return HomeRepositoryImpl(
            db = db,
            httpRequest = httpRequest
        )
    }
}