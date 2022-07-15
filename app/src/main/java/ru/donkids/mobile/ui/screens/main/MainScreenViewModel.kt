package ru.donkids.mobile.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.use_case.user.GetUser
import ru.donkids.mobile.ui.screens.main.entity.MainScreenEvent
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class MainScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    open fun onEvent(event: MainScreenEvent) = Unit

    sealed class Event {
        data class RequestLogin(
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
        println("init")
        viewModelScope.launch {
            getUser()?.let {
                catalogRepository.updateCatalog().collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            if (result.isCritical) {
                                eventChannel.send(Event.RequestLogin(result.message))
                            }
                        }
                        else -> Unit
                    }
                }
            } ?: eventChannel.send(Event.RequestLogin())
        }
    }

    override fun onEvent(event: MainScreenEvent) {
        viewModelScope.launch {
            /* TODO */
        }
    }
}