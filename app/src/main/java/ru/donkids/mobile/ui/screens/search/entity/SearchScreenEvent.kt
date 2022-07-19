package ru.donkids.mobile.ui.screens.search.entity

sealed class SearchScreenEvent {
    data class QueryChanged(
        val query: String
    ) : SearchScreenEvent()
}
