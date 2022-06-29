package ru.donkids.mobile.domain.use_case.user

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.donkids.mobile.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPreferences @Inject constructor(
    @ApplicationContext
    private val context: Context,
) {
    operator fun invoke(): SharedPreferences {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }
}