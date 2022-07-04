package ru.donkids.mobile.presentation.page_home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.donkids.mobile.domain.repository.HomeRepository
import ru.donkids.mobile.util.Resource
import javax.inject.Inject

abstract class HomePageViewModel : ViewModel() {
    var state by mutableStateOf(HomePageState())
        protected set

    open fun onEvent(event: HomePageEvent) = Unit
}

@HiltViewModel
class HomePageViewModelImpl @Inject constructor(
    private val repository: HomeRepository
) : HomePageViewModel() {
    init {
        getBanners()
    }

    private fun getBanners() {
        viewModelScope.launch {
            repository.getBanners().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(banners = result.data)
                    }
                    else -> Unit
                }
            }
        }
    }
}