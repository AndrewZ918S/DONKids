package ru.donkids.mobile.presentation.screen_main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.donkids.mobile.domain.use_case.user.GetUser
import javax.inject.Inject

abstract class MainScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(MainScreenState())
        protected set

    open fun onEvent(event: MainScreenEvent) = Unit

    sealed class Event {
        object RequestLogin : Event()
    }
}

@HiltViewModel
class MainScreenScreenViewModelImpl @Inject constructor(
    private val getUser: GetUser
) : MainScreenViewModel() {
    override fun onEvent(event: MainScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is MainScreenEvent.NavigationItemSelected -> {
                    state = state.copy(selectedPage = event.index)
                }
                is MainScreenEvent.CheckLogin -> {
                    getUser() ?: eventChannel.send(Event.RequestLogin)
                }
            }
        }
    }
}