package ru.donkids.mobile.domain.use_case.login.validate

import ru.donkids.mobile.R
import ru.donkids.mobile.util.DynamicText
import ru.donkids.mobile.util.ResText
import ru.donkids.mobile.util.StaticText
import javax.inject.Inject

class ValidatePassword @Inject constructor() {
    operator fun invoke(password: String): DynamicText? {
        return when {
            password.isBlank() -> {
                ResText(R.string.blank_password)
            }
            else -> null
        }
    }
}
