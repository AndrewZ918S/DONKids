package ru.donkids.mobile.data.remote.adapters

import com.squareup.moshi.FromJson
import ru.donkids.mobile.data.remote.enums.Error
import ru.donkids.mobile.data.remote.enums.Status

internal class EnumAdapter {
    @FromJson
    fun toStatus(value: String): Status {
        return when(value) {
            "ok" -> Status.OK
            else -> Status.ERROR
        }
    }

    @FromJson
    fun toError(value: String): Error {
        return when(value) {
            "chek-error" -> Error.CheckError
            "login-error" -> Error.LoginError
            "login-incorrect" -> Error.LoginIncorrect
            "login-need" -> Error.LoginNeed
            else -> Error.Unknown
        }
    }
}
