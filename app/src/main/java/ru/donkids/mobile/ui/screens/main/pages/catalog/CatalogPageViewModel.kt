package ru.donkids.mobile.ui.screens.main.pages.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.donkids.mobile.data.local.search.relevantSearch
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

    sealed interface Event {
        data class RequestLogin(
            val message: String
        ) : Event

        object OpenSearch : Event
        object NavBack : Event
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
                            categories = result.data
                        )
                        changeDestination(args.destinationId, args.query)
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
                    changeDestination(event.id)
                }
                is CatalogPageEvent.OpenSearch -> {
                    eventChannel.send(Event.OpenSearch)
                }
                is CatalogPageEvent.NavBack -> {
                    state.destination?.let { destination ->
                        state = state.copy(
                            destination = state.categories.find {
                                it.id == destination.parentId
                            },
                            products = null,
                            query = null
                        )
                    } ?: state.query?.let {
                        state = state.copy(
                            products = null,
                            query = null
                        )
                    } ?: eventChannel.send(Event.NavBack)
                }
            }
        }
    }

    private suspend fun changeDestination(id: Int, query: String? = null) {
        state = state.copy(
            destination = state.categories.find {
                it.id == id
            },
            query = query
        )
        if (state.categories.none { it.parentId == id }) {
            withContext(Dispatchers.IO) {

                if (id > 0) {
                    repository.getChildProducts(id).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                val products = result.data.filter { !it.isCategory }

                                state = state.copy(
                                    products = state.query?.let { query ->
                                        products.relevantSearch(query)
                                    } ?: products
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
                } else {
                    repository.getCatalog().collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                val products = result.data.filter { !it.isCategory }

                                state = state.copy(
                                    products = state.query?.let { query ->
                                        products.relevantSearch(query)
                                    } ?: products
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
        }
    }
}
