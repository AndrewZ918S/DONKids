package ru.donkids.mobile.presentation.page_catalog

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
    private val repository: CatalogRepository
) : CatalogPageViewModel() {
    init {
        getCategories()
    }

    override fun onEvent(event: CatalogPageEvent) {
        viewModelScope.launch {
            when (event) {
                is CatalogPageEvent.SelectCategory -> {
                    state = state.copy(
                        destination = state.categories.find { it.id == event.id }
                    )
                }
                is CatalogPageEvent.NavBack -> {
                    state.destination?.let { child ->
                        state = state.copy(
                            destination = state.categories.find { it.id == child.parentId }
                        )
                    } ?: eventChannel.send(Event.NavBack)
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(categories = result.data)
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
}
