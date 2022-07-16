package ru.donkids.mobile.ui.screens.product.entity

sealed class ProductScreenEvent {
    object AddToFavorite : ProductScreenEvent()
    object GoToFavorites : ProductScreenEvent()
    object GoToCart : ProductScreenEvent()
    object AddToCart : ProductScreenEvent()
    object RemoveFromCart : ProductScreenEvent()
    object CommitCart : ProductScreenEvent()
    data class EditCart(
        val value: String
    ) : ProductScreenEvent()
}
