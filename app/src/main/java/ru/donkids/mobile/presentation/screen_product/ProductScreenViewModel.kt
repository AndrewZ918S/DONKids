package ru.donkids.mobile.presentation.screen_product

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
import ru.donkids.mobile.data.mapper.toRecent
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class ProductScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(ProductScreenState())
        protected set

    open fun onEvent(event: ProductScreenEvent) = Unit

    sealed class Event {
        object Search : Event()
    }
}

@HiltViewModel
class ProductScreenViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val homeRepository: HomeRepository,
    savedStateHandle: SavedStateHandle
) : ProductScreenViewModel() {
    init {
        viewModelScope.launch {
            val id = with (savedStateHandle.get<Int>("productId")) {
                if (this != -1) {
                    this
                } else {
                    null
                }
            }
            val code = savedStateHandle.get<String>("productCode")

            val flow = id?.let {
                catalogRepository.getProductById(it, true)
            } ?: code?.let {
                catalogRepository.getProductByCode(it, true)
            } ?: return@launch

            flow.collect { product ->
                when (product) {
                    is Resource.Success -> {
                        val data = product.data
                        state = state.copy(
                            imageLink = DonKidsApi.SITE_URL + data.imageLink,
                            title = data.title,
                            productCode = data.code,
                            vendorCode = data.vendorCode,
                            isAvailable = data.isAvailable,
                            price = data.price
                        )
                        catalogRepository.getProductById(data.parentId).collect { parent ->
                            when (parent) {
                                is Resource.Success -> {
                                    state = state.copy(
                                        category = parent.data.abbreviation
                                    )
                                }
                                else -> Unit
                            }
                        }
                        homeRepository.updateHistory(data.toRecent())
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onEvent(event: ProductScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is ProductScreenEvent.OpenSearch -> {
                    eventChannel.send(Event.Search)
                }
            }
        }
    }
}