package ru.donkids.mobile.domain.use_case.login.validate

import android.util.Patterns
import ru.donkids.mobile.R
import ru.donkids.mobile.util.DynamicText
import ru.donkids.mobile.util.ResText
import ru.donkids.mobile.util.StaticText
import javax.inject.Inject

class ValidateEmail @Inject constructor() {
    operator fun invoke(email: String): DynamicText? {
        return when {
            email.isBlank() -> {
                ResText(R.string.blank_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                ResText(R.string.invalid_email)
            }
            else -> null
        }
    }
}
