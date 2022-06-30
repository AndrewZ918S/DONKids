package ru.donkids.mobile.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.use_case.OpenChromeUrl
import ru.donkids.mobile.domain.use_case.login.LoginManual
import ru.donkids.mobile.domain.use_case.login.validate.ValidateEmail
import ru.donkids.mobile.domain.use_case.login.validate.ValidatePassword
import ru.donkids.mobile.util.ResText
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.StaticText
import javax.inject.Inject

abstract class LoginViewModel : ViewModel() {
    protected val serverEventChannel = Channel<User?>()
    val serverEvents = serverEventChannel.receiveAsFlow()

    var state by mutableStateOf(LoginFormState())
        protected set

    open fun onEvent(event: LoginFormEvent) = Unit
}

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val loginManual: LoginManual,
    private val openChromeUrl: OpenChromeUrl
) : LoginViewModel() {
    override fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                if (state.emailError != null) {
                    state = state.copy(emailError = validateEmail(event.email))
                }
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                if (state.passwordError != null) {
                    state = state.copy(passwordError = validatePassword(event.password))
                }
            }
            is LoginFormEvent.ShowPassword -> {
                state = state.copy(showPassword = event.isVisible)
            }
            is LoginFormEvent.Login -> {
                val emailError = validateEmail(state.email)
                val passwordError = validatePassword(state.password)
                state = state.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )

                if (listOf(emailError, passwordError).any { it != null })
                    return

                loginManual(state.email, state.password).onEach {
                    when (it) {
                        is Resource.Success -> {
                            serverEventChannel.send(it.data)
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                serverError = when (it.message) {
                                    "login-error" -> {
                                        ResText(R.string.login_error)
                                    }
                                    "network-error" -> {
                                        ResText(R.string.network_error)
                                    }
                                    else -> {
                                        ResText(R.string.unknown_error)
                                    }
                                }
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = it.isLoading)
                            if (it.isLoading) {
                                state = state.copy(serverError = StaticText())
                            }
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is LoginFormEvent.Signup -> {
                openChromeUrl(event.context, DonKidsApi.SIGNUP_URL)
            }
            is LoginFormEvent.Restore -> {
                openChromeUrl(event.context, DonKidsApi.RESTORE_URL)
            }
        }
    }
}
