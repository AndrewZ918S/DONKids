package ru.donkids.mobile.presentation.screen_login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.User
import ru.donkids.mobile.domain.use_case.login.LoginManual
import ru.donkids.mobile.domain.use_case.validate.ValidateEmail
import ru.donkids.mobile.domain.use_case.validate.ValidatePassword
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class LoginScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(LoginScreenState())
        protected set

    open fun onEvent(event: LoginScreenEvent) = Unit

    sealed class Event {
        data class OpenUrl(
            val url: String
        ) : Event()

        data class Proceed(
            val user: User
        ) : Event()
    }
}

@HiltViewModel
class LoginScreenScreenViewModelImpl @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
    private val loginManual: LoginManual,
    savedStateHandle: SavedStateHandle
) : LoginScreenViewModel() {
    init {
        val message = savedStateHandle.get<String>("message")

        state = state.copy(
            serverError = when (message) {
                "null" -> ""
                null -> ""
                else -> message
            }
        )
    }

    override fun onEvent(event: LoginScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginScreenEvent.EmailChanged -> {
                    state = state.copy(email = event.email)
                    if (state.emailError != null) {
                        state = state.copy(emailError = validateEmail(event.email))
                    }
                }
                is LoginScreenEvent.PasswordChanged -> {
                    state = state.copy(password = event.password)
                    if (state.passwordError != null) {
                        state = state.copy(passwordError = validatePassword(event.password))
                    }
                }
                is LoginScreenEvent.ShowPassword -> {
                    state = state.copy(showPassword = event.isVisible)
                }
                is LoginScreenEvent.Login -> {
                    val emailError = validateEmail(state.email)
                    val passwordError = validatePassword(state.password)
                    state = state.copy(
                        emailError = emailError,
                        passwordError = passwordError
                    )

                    if (listOf(emailError, passwordError).any { it != null })
                        return@launch

                    loginManual(state.email, state.password).collect {
                        when (it) {
                            is Resource.Success -> {
                                eventChannel.send(Event.Proceed(it.data))
                            }
                            is Resource.Error -> {
                                state = state.copy(serverError = it.message)
                            }
                            is Resource.Loading -> {
                                state = state.copy(isLoading = it.isLoading)
                                if (it.isLoading) {
                                    state = state.copy(serverError = "")
                                }
                            }
                        }
                    }
                }
                is LoginScreenEvent.Signup -> {
                    eventChannel.send(Event.OpenUrl(DonKidsApi.SITE_URL + DonKidsApi.SIGNUP_PATH))
                }
                is LoginScreenEvent.Restore -> {
                    eventChannel.send(Event.OpenUrl(DonKidsApi.SITE_URL + DonKidsApi.RESTORE_PATH))
                }
            }
        }
    }
}
