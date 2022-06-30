package ru.donkids.mobile.presentation.login_screen

import android.content.Context

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class ShowPassword(val isVisible: Boolean) : LoginFormEvent()

    object Login: LoginFormEvent()
    data class Signup(val context: Context): LoginFormEvent()
    data class Restore(val context: Context): LoginFormEvent()
}
