package ru.donkids.mobile.domain.use_case.user

import ru.donkids.mobile.domain.model.User
import javax.inject.Inject

class SetUser @Inject constructor(
    private val getUserPreferences: GetUserPreferences
) {
    operator fun invoke(user: User?) {
        getUserPreferences().edit()
            .putString("id", user?.id)
            .putString("name", user?.name)
            .apply()
    }
}