package ru.donkids.mobile.ui.screens.main.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.repository.CatalogRepository
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.ui.screens.main.pages.home.entity.HomePageEvent
import ru.donkids.mobile.ui.screens.main.pages.home.entity.HomePageState
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class HomePageViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(HomePageState())
        protected set

    open fun onEvent(event: HomePageEvent) = Unit

    sealed class Event {
        data class OpenUrl(
            val url: String
        ) : Event()
        data class OpenProduct(
            val productId: Int? = null,
            val productCode: String? = null
        ) : Event()
        data class ShowMessage(
            val message: String
        ) : Event()
    }
}

@HiltViewModel
class HomePageViewModelImpl @Inject constructor(
    private val catalogRepository: CatalogRepository,
    private val homeRepository: HomeRepository
) : HomePageViewModel() {
    private fun refresh() {
        viewModelScope.launch {
            homeRepository.getHistory().collect { result ->
                if (result is Resource.Success) {
                    state = state.copy(history = result.data)
                }
            }
            homeRepository.getBanners().collect { result ->
                if (result is Resource.Success) {
                    state = state.copy(banners = result.data)
                }
            }
        }
    }

    override fun onEvent(event: HomePageEvent) {
        viewModelScope.launch {
            when (event) {
                is HomePageEvent.OpenBanner -> {
                    event.banner.productCode?.let { productCode ->
                        catalogRepository.getProductByCode(productCode, false).collect { result ->
                            when (result) {
                                is Resource.Success -> {
                                    eventChannel.send(Event.OpenProduct(productCode = productCode))
                                }
                                is Resource.Error -> {
                                    eventChannel.send(Event.ShowMessage(result.message))
                                }
                                else -> Unit
                            }
                        }
                    } ?: run {
                        val url = event.banner.getPageLink()

                        if (url != DonKidsApi.SITE_URL) {
                            eventChannel.send(Event.OpenUrl(url))
                        }
                    }
                }
                is HomePageEvent.OpenRecent -> {
                    eventChannel.send(Event.OpenProduct(productId = event.recent.id))
                }
                is HomePageEvent.Refresh -> {
                    refresh()
                }
            }
        }
    }
}
