package ru.donkids.mobile.ui.screens.product

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
import ru.donkids.mobile.R
import ru.donkids.mobile.data.mapper.toRecent
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.domain.use_case.localize.ProductSpecs
import ru.donkids.mobile.domain.use_case.localize.StringResource
import ru.donkids.mobile.ui.screens.destinations.ProductScreenDestination
import ru.donkids.mobile.ui.screens.product.entity.ProductScreenEvent
import ru.donkids.mobile.ui.screens.product.entity.ProductScreenState
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class ProductScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(ProductScreenState())
        protected set

    open fun onEvent(event: ProductScreenEvent) = Unit

    sealed class Event {
        data class ShowMessage(
            val message: String
        ) : Event()
    }
}

@HiltViewModel
class ProductScreenViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val homeRepository: HomeRepository,
    private val productSpecs: ProductSpecs,
    private val stringResource: StringResource,
    savedStateHandle: SavedStateHandle
) : ProductScreenViewModel() {
    init {
        viewModelScope.launch {
            val args = ProductScreenDestination.argsFrom(savedStateHandle)

            val flow = args.id?.let {
                catalogRepository.getProductById(it, true)
            } ?: args.code?.let {
                catalogRepository.getProductByCode(it, true)
            } ?: return@launch

            flow.collect { product ->
                when (product) {
                    is Resource.Success -> {
                        val data = product.data

                        state = state.copy(
                            imageLink = data.getImageLink(),
                            title = data.title,
                            productCode = data.code,
                            vendorCode = data.vendorCode,
                            properties = productSpecs(data.properties),
                            isAvailable = data.isAvailable,
                            price = data.price
                        )

                        catalogRepository.getProductById(data.parentId, true).collect { parent ->
                            when (parent) {
                                is Resource.Success -> {
                                    state = state.copy(
                                        category = parent.data.title
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
                is ProductScreenEvent.GoToFavorites -> {
                    /* TODO */
                }
                is ProductScreenEvent.GoToCart -> {
                    /* TODO */
                }
                is ProductScreenEvent.CommitCart -> {
                    if (state.inCart < 1) {
                        state = state.copy(
                            inCart = 1
                        )
                    }
                }
                is ProductScreenEvent.AddToCart -> {
                    state = if (state.isInCart) {
                        state.copy(
                            inCart = (state.inCart + 1).coerceAtMost(999)
                        )
                    } else {
                        eventChannel.send(
                            Event.ShowMessage(
                                stringResource(R.string.added_to_cart)
                            )
                        )
                        state.copy(
                            isInCart = true,
                            inCart = 1
                        )
                    }
                }
                is ProductScreenEvent.RemoveFromCart -> {
                    if (state.inCart > 1) {
                        state = state.copy(
                            inCart = (state.inCart - 1)
                        )
                    }
                }
                is ProductScreenEvent.EditCart -> {
                    state = state.copy(
                        inCart = event.value
                            .replace(Regex("[^\\d]+"), "")
                            .removePrefix("0")
                            .take(3)
                            .toIntOrNull() ?: 0
                    )
                }
                is ProductScreenEvent.AddToFavorite -> {
                    if (!state.isFavorite) {
                        eventChannel.send(
                            Event.ShowMessage(
                                stringResource(R.string.added_to_favorites)
                            )
                        )
                        state = state.copy(
                            isFavorite = true
                        )
                    }
                }
            }
        }
    }
}
