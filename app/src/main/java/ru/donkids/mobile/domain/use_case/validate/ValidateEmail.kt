package ru.donkids.mobile.domain.use_case.validate

import android.util.Patterns
import ru.donkids.mobile.R
import ru.donkids.mobile.domain.use_case.localize.StringResource
import javax.inject.Inject

class ValidateEmail @Inject constructor(
    private val stringResource: StringResource
) {
    operator fun invoke(email: String): String? {
        return when {
            email.isBlank() -> {
                stringResource(R.string.blank_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                stringResource(R.string.invalid_email)
            }
            else -> null
        }
    }
}
