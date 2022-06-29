package ru.donkids.mobile.presentation.login_screen

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class ShowPassword(val isVisible: Boolean) : LoginFormEvent()

    object Login: LoginFormEvent()
    object Signup: LoginFormEvent()
    object Restore: LoginFormEvent()
}
