package ru.donkids.mobile.domain.use_case.localize

import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

class ServerError @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun <T> invoke(errorType: Error? = null) = Resource.Error<T>(
        stringResource(
            when (errorType) {
                Error.CheckError -> R.string.check_error
                Error.LoginError -> R.string.login_error
                Error.LoginIncorrect -> R.string.login_incorrect
                Error.LoginNeed -> R.string.unknown_error
                else -> R.string.unknown_error
            }
        )
    )
}
