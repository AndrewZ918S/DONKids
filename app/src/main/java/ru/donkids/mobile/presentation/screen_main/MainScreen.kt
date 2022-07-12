package ru.donkids.mobile.presentation.screen_main

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.Destinations
import ru.donkids.mobile.presentation.page_catalog.CatalogPage
import ru.donkids.mobile.presentation.page_home.HomePage
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor
import ru.donkids.mobile.presentation.ui.theme.get

sealed class MainScreen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val defaultIcon: Int
) {
    object Home : MainScreen(
        route = "home",
        label = R.string.home,
        selectedIcon = R.drawable.ic_home_filled,
        defaultIcon = R.drawable.ic_home_outline
    )

    object Catalog : MainScreen(
        route = "catalog",
        label = R.string.catalog,
        selectedIcon = R.drawable.ic_catalog_filled,
        defaultIcon = R.drawable.ic_catalog_outline
    )

    object Cart : MainScreen(
        route = "cart",
        label = R.string.cart,
        selectedIcon = R.drawable.ic_cart_filled,
        defaultIcon = R.drawable.ic_cart_outline
    )

    object Favorite : MainScreen(
        route = "favorite",
        label = R.string.favorite,
        selectedIcon = R.drawable.ic_favorite_filled,
        defaultIcon = R.drawable.ic_favorite_outline
    )

    object More : MainScreen(
        route = "more",
        label = R.string.more,
        selectedIcon = R.drawable.ic_account_filled,
        defaultIcon = R.drawable.ic_account_outline
    )
}

@Composable
fun MainScreen(navController: NavController? = null) {
    val viewModel: MainScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : MainScreenViewModel() {}
        false -> hiltViewModel<MainScreenScreenViewModelImpl>()
    }
    val mainNavController = rememberNavController()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is MainScreenViewModel.Event.RequestLogin -> {
                    navController?.navigate(
                        route = "${Destinations.LOGIN}?msg=${it.message}"
                    )
                }
            }
        }
    }

    BackHandler(
        enabled = currentDestination?.route != MainScreen.Home.route,
        onBack = {
            mainNavController.navigate(MainScreen.Home.route) {
                popUpTo(mainNavController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface[2]
    )

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val items = listOf(
        MainScreen.Home,
        MainScreen.Catalog,
        MainScreen.Cart,
        MainScreen.Favorite,
        MainScreen.More,
    )

    Scaffold(
        content = { innerPadding ->
            NavHost(
                navController = mainNavController,
                startDestination = MainScreen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(MainScreen.Home.route) {
                    HomePage(navController, snackbarHostState)
                }
                composable(MainScreen.Catalog.route) {
                    CatalogPage(navController)
                }
                composable(MainScreen.Cart.route) {
                    HomePage(navController, snackbarHostState)
                }
                composable(MainScreen.Favorite.route) {
                    HomePage(navController, snackbarHostState)
                }
                composable(MainScreen.More.route) {
                    HomePage(navController, snackbarHostState)
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    val selected = currentDestination?.route == screen.route

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(
                                    if (selected) {
                                        screen.selectedIcon
                                    } else {
                                        screen.defaultIcon
                                    }
                                ),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(screen.label)
                            )
                        },
                        selected = selected,
                        onClick = {
                            mainNavController.navigate(screen.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        MainScreen()
    }
}
