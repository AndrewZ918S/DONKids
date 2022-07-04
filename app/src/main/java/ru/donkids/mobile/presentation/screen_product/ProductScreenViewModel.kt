package ru.donkids.mobile.presentation.screen_product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class ProductScreenViewModel : ViewModel() {
    protected val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(ProductScreenState())
        protected set

    open fun onEvent(event: ProductScreenEvent) = Unit

    sealed class Event {
        object Close : Event()
        object Search : Event()
    }
}

@HiltViewModel
class ProductScreenViewModelImpl @Inject constructor(

) : ProductScreenViewModel() {
    override fun onEvent(event: ProductScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is ProductScreenEvent.BackPressed -> {
                    eventChannel.send(Event.Close)
                }
            }
        }
    }
}