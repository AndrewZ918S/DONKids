package ru.donkids.mobile.presentation.screen_main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.scope.DestinationScope
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.destinations.CatalogPageDestination
import ru.donkids.mobile.presentation.destinations.DirectionDestination
import ru.donkids.mobile.presentation.destinations.HomePageDestination
import ru.donkids.mobile.presentation.page_catalog.CatalogPage
import ru.donkids.mobile.presentation.page_home.HomePage

sealed class MainScreenNavigation(
    val destination: DirectionDestination,
    val content: @Composable DestinationScope<Unit>.(
        DestinationsNavigator?,
        SnackbarHostState,
        NavController?
    ) -> Unit,
    @StringRes
    val label: Int,
    @DrawableRes
    val selectedIcon: Int,
    @DrawableRes
    val defaultIcon: Int
) {
    object Home : MainScreenNavigation(
        destination = HomePageDestination,
        content = { navigator, snackbarState, _ ->
            HomePage(
                navigator = navigator,
                snackbarHostState = snackbarState,
            )
        },
        label = R.string.home,
        selectedIcon = R.drawable.ic_home_filled,
        defaultIcon = R.drawable.ic_home_outline
    )

    object Catalog : MainScreenNavigation(
        destination = CatalogPageDestination,
        content = { navigator, snackbarState, navController ->
            CatalogPage(
                navigator = navigator,
                snackbarHostState = snackbarState,
                navController = navController
            )
        },
        label = R.string.catalog,
        selectedIcon = R.drawable.ic_catalog_filled,
        defaultIcon = R.drawable.ic_catalog_outline
    )
/*
    object Cart : MainScreenNavigation(
        destination = HomePageDestination,
        content = { navigator, snackbarState, _ ->
            HomePage(
                navigator = navigator,
                snackbarHostState = snackbarState,
            )
        },
        label = R.string.cart,
        selectedIcon = R.drawable.ic_cart_filled,
        defaultIcon = R.drawable.ic_cart_outline
    )

    object Favorite : MainScreenNavigation(
        destination = HomePageDestination,
        content = { navigator, snackbarState, _ ->
            HomePage(
                navigator = navigator,
                snackbarHostState = snackbarState,
            )
        },
        label = R.string.favorite,
        selectedIcon = R.drawable.ic_favorite_filled,
        defaultIcon = R.drawable.ic_favorite_outline
    )

    object More : MainScreenNavigation(
        destination = HomePageDestination,
        content = { navigator, snackbarState, _ ->
            HomePage(
                navigator = navigator,
                snackbarHostState = snackbarState,
            )
        },
        label = R.string.more,
        selectedIcon = R.drawable.ic_account_filled,
        defaultIcon = R.drawable.ic_account_outline
    )*/
}
