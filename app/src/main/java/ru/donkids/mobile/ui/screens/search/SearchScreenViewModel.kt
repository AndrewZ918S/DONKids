package ru.donkids.mobile.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.ui.screens.search.entity.SearchScreenEvent
import ru.donkids.mobile.ui.screens.search.entity.SearchScreenState
import ru.donkids.mobile.util.Resource
import ru.donkids.mobile.util.jaroWinkler
import javax.inject.Inject

abstract class SearchScreenViewModel : ViewModel() {
    var state by mutableStateOf(SearchScreenState())
        protected set

    open fun onEvent(event: SearchScreenEvent) = Unit
}

@HiltViewModel
class SearchScreenViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository
) : SearchScreenViewModel() {
    var catalog: List<Product>? = null
    var sortScope: CoroutineScope? = null

    init {
        viewModelScope.launch {
            catalogRepository.getCatalog().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        catalog = result.data
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onEvent(event: SearchScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchScreenEvent.QueryChanged -> {
                    state = state.copy(query = event.query)

                    sortScope?.cancel()
                    withContext(Dispatchers.IO) {
                        sortScope = this

                        catalog?.let { catalog ->
                            val products = catalog
                                .filter {
                                    !it.isCategory
                                }
                                .sortedByDescending {
                                    it.title.jaroWinkler(event.query)
                                }
                                .take(15)

                            val categories = catalog
                                .filter {
                                    it.isCategory
                                }
                                .sortedByDescending {
                                    it.title.jaroWinkler(event.query)
                                }

                            val parents = categories
                                .filter { parent ->
                                    products.any { it.parentId == parent.id }
                                }
                                .sortedBy { parent ->
                                    products.indexOfFirst { it.parentId == parent.parentId }
                                }

                            state = state.copy(
                                products = products.take(5),
                                categories = categories.take(3),
                                parents = parents.take(3)
                            )
                        }
                    }
                }
            }
        }
    }
}
