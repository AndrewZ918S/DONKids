package ru.donkids.mobile.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.use_case.user.GetUser
import ru.donkids.mobile.ui.screens.main.entity.MainScreenState
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class MainScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(MainScreenState())
        protected set

    sealed class Event {
        data class RequestLogin(
            val message: String? = null
        ) : Event()

        data class ShowMessage(
            val message: String? = null
        ) : Event()
    }
}

@HiltViewModel
class MainScreenScreenViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val getUser: GetUser
) : MainScreenViewModel() {
    init {
        viewModelScope.launch {
            getUser()?.let {
                catalogRepository.getCatalog(true).collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            eventChannel.send(
                                if (result.isCritical) {
                                    Event.RequestLogin(result.message)
                                } else {
                                    Event.ShowMessage(result.message)
                                }
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                        else -> Unit
                    }
                }
            } ?: eventChannel.send(Event.RequestLogin())
        }
    }
}
