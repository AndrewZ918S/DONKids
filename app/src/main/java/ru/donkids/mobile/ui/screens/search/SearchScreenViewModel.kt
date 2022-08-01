package ru.donkids.mobile.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.donkids.mobile.data.local.search.relevantSearch
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.ui.screens.destinations.SearchScreenDestination
import ru.donkids.mobile.ui.screens.search.entity.SearchScreenEvent
import ru.donkids.mobile.ui.screens.search.entity.SearchScreenState
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.radixSortedBy
import javax.inject.Inject

abstract class SearchScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(SearchScreenState())
        protected set

    open fun onEvent(event: SearchScreenEvent) = Unit

    sealed class Event {
        data class OpenCatalog(
            val id: Int,
            val query: String?
        ) : Event()
    }
}

@HiltViewModel
class SearchScreenViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository,
    savedStateHandle: SavedStateHandle
) : SearchScreenViewModel() {
    var catalog: List<Product>? = null
    var sortScope: CoroutineScope? = null

    init {
        viewModelScope.launch {
            val args = SearchScreenDestination.argsFrom(savedStateHandle)

            args.query?.let { query ->
                state = state.copy(
                    query = query
                )
            }

            catalogRepository.getCatalog().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        catalog = result.data

                        startSearch()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onEvent(event: SearchScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchScreenEvent.OpenCatalog -> {
                    eventChannel.send(
                        Event.OpenCatalog(
                            id = event.id,
                            query = event.query
                        )
                    )
                }
                is SearchScreenEvent.QueryChanged -> {
                    state = state.copy(query = event.query)

                    startSearch()
                }
            }
        }
    }

    private fun startSearch() {
        viewModelScope.launch {
            if (state.query.isNotEmpty()) {
                sortScope?.cancel()
                withContext(Dispatchers.IO) {
                    sortScope = this

                    catalog?.let { catalog ->
                        val searchResults = catalog.relevantSearch(state.query)

                        val products = searchResults.filter { !it.isCategory }

                        val categories = searchResults.filter { it.isCategory }

                        val parents = catalog
                            .filter { product ->
                                product.isCategory
                            }
                            .filter { parent ->
                                products.any { it.parentId == parent.id }
                            }
                            .radixSortedBy { parent ->
                                products.indexOfFirst { it.parentId == parent.id }
                            }

                        state = state.copy(
                            products = products.take(5),
                            categories = categories.take(3),
                            parents = parents.take(3)
                        )
                    }
                }
            } else {
                state = state.copy(
                    products = ArrayList(),
                    categories = ArrayList(),
                    parents = ArrayList()
                )
            }
        }
    }
}
