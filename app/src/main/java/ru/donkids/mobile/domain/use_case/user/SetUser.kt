package ru.donkids.mobile.domain.use_case.user

import ru.donkids.mobile.domain.model.User
import javax.inject.Inject

class SetUser @Inject constructor(
    private val getUserPreferences: GetUserPreferences
) {
    operator fun invoke(user: User?) {
        val editor = getUserPreferences().edit()

        editor.putString("id", user?.id)
            .putString("name", user?.name)
            .putLong("timestamp", user?.timestamp ?: 0)
            .apply()
    }
}