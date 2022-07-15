package ru.donkids.mobile.ui.screens.main.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.donkids.mobile.R

data class MainScreenState(
    val selectedPage: Int = 0,
    val pages: List<NavigationItem> = listOf(
        NavigationItem(
            label = R.string.home,
            iconSelected = R.drawable.ic_home_filled,
            iconDefault = R.drawable.ic_home_outline
        ),
        NavigationItem(
            label = R.string.catalog,
            iconSelected = R.drawable.ic_catalog_filled,
            iconDefault = R.drawable.ic_catalog_outline
        ),
        NavigationItem(
            label = R.string.cart,
            iconSelected = R.drawable.ic_cart_filled,
            iconDefault = R.drawable.ic_cart_outline
        ),
        NavigationItem(
            label = R.string.favorite,
            iconSelected = R.drawable.ic_favorite_filled,
            iconDefault = R.drawable.ic_favorite_outline
        ),
        NavigationItem(
            label = R.string.more,
            iconSelected = R.drawable.ic_account_filled,
            iconDefault = R.drawable.ic_account_outline
        ),
    )
)

data class NavigationItem(
    @StringRes var label: Int,
    @DrawableRes var iconSelected: Int,
    @DrawableRes var iconDefault: Int
)
