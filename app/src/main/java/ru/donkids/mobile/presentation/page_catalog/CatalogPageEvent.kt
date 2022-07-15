package ru.donkids.mobile.presentation.page_catalog

sealed class CatalogPageEvent {
    data class SelectCategory(
        val id: Int
    ) : CatalogPageEvent()

    object NavBack: CatalogPageEvent()
}
