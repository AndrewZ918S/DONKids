package ru.donkids.mobile.ui.screens.main.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationSpec
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.screens.destinations.CatalogPageDestination
import ru.donkids.mobile.ui.screens.destinations.HomePageDestination
import ru.donkids.mobile.ui.screens.main.pages.catalog.CatalogPage
import ru.donkids.mobile.ui.screens.main.pages.catalog.entity.CatalogPageNavArgs
import ru.donkids.mobile.ui.screens.main.pages.home.HomePage

sealed class MainScreenNavigation<T>(
    val destination: DestinationSpec<T>,
    val content: @Composable (
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
    object Home : MainScreenNavigation<Unit>(
        destination = HomePageDestination,
        content = { navigator, snackbarState, navController ->
            HomePage(
                navigator = navigator,
                snackbarHostState = snackbarState,
                navController
            )
        },
        label = R.string.home,
        selectedIcon = R.drawable.ic_home_filled,
        defaultIcon = R.drawable.ic_home_outline
    )

    object Catalog : MainScreenNavigation<CatalogPageNavArgs>(
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
