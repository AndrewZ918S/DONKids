package ru.donkids.mobile.ui.screens.main.pages.catalog.entity

sealed class CatalogPageEvent {
    data class SelectCategory(
        val id: Int
    ) : CatalogPageEvent()

    object NavBack: CatalogPageEvent()
}
