package ru.donkids.mobile.domain.use_case.validate

import ru.donkids.mobile.R
import ru.donkids.mobile.domain.use_case.StringResource
import javax.inject.Inject

class ValidatePassword @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun invoke(password: String): String? {
        return when {
            password.isBlank() -> {
                stringResource(R.string.blank_password)
            }
            else -> null
        }
    }
}
