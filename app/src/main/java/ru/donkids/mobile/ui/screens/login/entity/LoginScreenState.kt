package ru.donkids.mobile.ui.screens.login.entity

data class LoginScreenState(
    val isLoading: Boolean = false,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val showPassword: Boolean = false,
    val serverError: String? = null,
)
