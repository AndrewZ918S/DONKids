package ru.donkids.mobile.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.donkids.mobile.R
import ru.donkids.mobile.domain.use_case.localize.StringResource
import ru.donkids.mobile.util.Resource
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class HttpRequest @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun <T> invoke(block: suspend FlowCollector<Resource<T>>.() -> Unit) = flow {
        emit(Resource.Loading(true))
        try {
            block()
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = stringResource(
                        when (e) {
                            is IOException -> R.string.network_error
                            is SocketTimeoutException -> R.string.network_timeout
                            else -> R.string.unknown_error
                        }
                    ),
                    critical = false
                )
            )
            e.printStackTrace()
        }
        emit(Resource.Loading(false))
    }.flowOn(Dispatchers.IO)
}
