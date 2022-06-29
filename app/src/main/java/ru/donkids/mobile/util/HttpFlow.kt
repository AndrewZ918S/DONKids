package ru.donkids.mobile.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException
import java.io.IOException

class HttpFlow<T>(private val block: suspend FlowCollector<Resource<T>>.() -> Unit) :
    Flow<Resource<T>> {
    override suspend fun collect(collector: FlowCollector<Resource<T>>) {
        collector.emit(Resource.Loading(true))
        try {
            collector.block()
        } catch (e: HttpException) {
            collector.emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            collector.emit(Resource.Error("network-error"))
        }
        collector.emit(Resource.Loading(false))
    }
}

fun <T> http(block: suspend FlowCollector<Resource<T>>.() -> Unit): Flow<Resource<T>> =
    HttpFlow(block)
