package ru.donkids.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.donkids.mobile.domain.model.Banner
import ru.donkids.mobile.domain.model.Recent
import ru.donkids.mobile.util.Resource

interface HomeRepository {
    suspend fun getBanners(): Flow<Resource<List<Banner>>>

    suspend fun getHistory(): Flow<Resource<List<Recent>>>

    suspend fun updateHistory(recent: Recent)
}