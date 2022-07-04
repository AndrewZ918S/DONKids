package ru.donkids.mobile.domain.use_case.localize

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringResource @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    operator fun invoke(@StringRes id: Int, vararg formatArgs: Any): String {
        return context.getString(id, *formatArgs)
    }
}