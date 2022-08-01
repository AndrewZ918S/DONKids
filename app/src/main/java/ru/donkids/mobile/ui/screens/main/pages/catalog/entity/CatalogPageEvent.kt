package ru.donkids.mobile.ui.screens.main.pages.catalog.entity

sealed interface CatalogPageEvent {
    data class SelectCategory(
        val id: Int
    ) : CatalogPageEvent

    object NavBack: CatalogPageEvent
    object OpenSearch : CatalogPageEvent
}
