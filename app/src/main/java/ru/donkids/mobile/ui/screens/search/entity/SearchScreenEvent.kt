package ru.donkids.mobile.ui.screens.search.entity

sealed class SearchScreenEvent {
    data class QueryChanged(
        val query: String
    ) : SearchScreenEvent()

    data class OpenCatalog(
        val id: Int = -1,
        val query: String? = null
    ) : SearchScreenEvent()
}
