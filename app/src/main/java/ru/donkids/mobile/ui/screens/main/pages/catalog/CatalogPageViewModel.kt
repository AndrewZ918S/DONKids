package ru.donkids.mobile.ui.screens.main.pages.catalog

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
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.ui.screens.destinations.CatalogPageDestination
import ru.donkids.mobile.ui.screens.main.pages.catalog.entity.CatalogPageEvent
import ru.donkids.mobile.ui.screens.main.pages.catalog.entity.CatalogPageState
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class CatalogPageViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(CatalogPageState())
        protected set

    open fun onEvent(event: CatalogPageEvent) = Unit

    sealed class Event {
        data class RequestLogin(
            val message: String
        ) : Event()

        object NavBack : Event()
    }
}

@HiltViewModel
class CatalogPageViewModelImpl @Inject constructor(
    private val repository: CatalogRepository,
    savedStateHandle: SavedStateHandle
) : CatalogPageViewModel() {
    init {
        viewModelScope.launch {
            val args = CatalogPageDestination.argsFrom(savedStateHandle)

            repository.getCategories().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            destination = result.data.find {
                                it.id == args.destinationId
                            },
                            categories = result.data
                        )
                    }
                    is Resource.Error -> {
                        if (result.isCritical) {
                            eventChannel.send(Event.RequestLogin(result.message))
                        }
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onEvent(event: CatalogPageEvent) {
        viewModelScope.launch {
            when (event) {
                is CatalogPageEvent.SelectCategory -> {
                    state = state.copy(
                        destination = state.categories.find {
                            it.id == event.id
                        }
                    )
                }
                is CatalogPageEvent.NavBack -> {
                    state.destination?.let { destination ->
                        state = state.copy(
                            destination = state.categories.find {
                                it.id == destination.parentId
                            }
                        )
                    } ?: eventChannel.send(Event.NavBack)
                }
            }
        }
    }
}
