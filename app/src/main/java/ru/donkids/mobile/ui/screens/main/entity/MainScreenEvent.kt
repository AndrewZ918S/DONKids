package ru.donkids.mobile.ui.screens.main.entity

sealed interface MainScreenEvent {
    data class OpenCatalog(
        val destinationId: Int?,
        val query: String?
    ) : MainScreenEvent
}
