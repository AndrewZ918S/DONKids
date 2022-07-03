package ru.donkids.mobile.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import ru.donkids.mobile.R
import ru.donkids.mobile.util.Resource
import java.io.IOException
import javax.inject.Inject

class HttpRequest @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun <T> invoke(block: suspend FlowCollector<Resource<T>>.() -> Unit): Flow<Resource<T>> =
        flow {
            emit(Resource.Loading(true))
            try {
                block()
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: stringResource(R.string.unknown_error)))
            } catch (e: IOException) {
                emit(Resource.Error(stringResource(R.string.network_error)))
            }
            emit(Resource.Loading(false))
        }.flowOn(Dispatchers.IO)
}