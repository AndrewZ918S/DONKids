package ru.donkids.mobile.presentation.screen_product

sealed class ProductScreenEvent {
    object OpenSearch : ProductScreenEvent()
    object ToggleFavorite : ProductScreenEvent()
    object GoToCart : ProductScreenEvent()
    object AddToCart : ProductScreenEvent()
    object RemoveFromCart : ProductScreenEvent()
    object CommitCart : ProductScreenEvent()
    data class EditCart(
        val value: String
    ) : ProductScreenEvent()
}
