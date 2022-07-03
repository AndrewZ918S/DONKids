package ru.donkids.mobile.domain.use_case.user

import ru.donkids.mobile.domain.model.User
import javax.inject.Inject

class GetUser @Inject constructor(
    private val getUserPreferences: GetUserPreferences,
) {
    operator fun invoke(): User? {
        val preferences = getUserPreferences()

        val id = preferences.getString("id", "")
        val name = preferences.getString("name", "")

        return if (!id.isNullOrEmpty() && !name.isNullOrEmpty()) {
            User(id, name)
        } else {
            null
        }
    }
}
