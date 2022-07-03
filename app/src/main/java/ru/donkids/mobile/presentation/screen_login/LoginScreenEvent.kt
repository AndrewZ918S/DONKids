package ru.donkids.mobile.presentation.screen_login

sealed class LoginScreenEvent {
    data class EmailChanged(val email: String) : LoginScreenEvent()
    data class PasswordChanged(val password: String) : LoginScreenEvent()
    data class ShowPassword(val isVisible: Boolean) : LoginScreenEvent()

    object Login: LoginScreenEvent()
    object Signup: LoginScreenEvent()
    object Restore: LoginScreenEvent()
}
