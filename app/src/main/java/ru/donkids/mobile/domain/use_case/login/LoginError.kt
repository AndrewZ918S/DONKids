package ru.donkids.mobile.domain.use_case.login

import ru.donkids.mobile.R
import ru.donkids.mobile.domain.use_case.StringResource
import javax.inject.Inject

class LoginError @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun invoke(errorType: String) = stringResource(
        when (errorType) {
            "login-error" -> R.string.login_error
            else -> R.string.unknown_error
        }
    )
}
