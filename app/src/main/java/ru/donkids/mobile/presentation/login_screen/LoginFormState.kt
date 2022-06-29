package ru.donkids.mobile.presentation.login_screen

import ru.donkids.mobile.util.DynamicText

data class LoginFormState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: DynamicText? = null,
    val password: String = "",
    val passwordError: DynamicText? = null,
    val showPassword: Boolean = false,
    val serverError: DynamicText? = null,
)
