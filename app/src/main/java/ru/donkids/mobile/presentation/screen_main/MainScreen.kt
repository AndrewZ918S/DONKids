package ru.donkids.mobile.presentation.screen_main

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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import ru.donkids.mobile.presentation.NavGraphs
import ru.donkids.mobile.presentation.destinations.LoginScreenDestination
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor
import ru.donkids.mobile.presentation.ui.theme.get

@RootNavGraph(
    start = true
)
@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator? = null
) {
    val viewModel: MainScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : MainScreenViewModel() {}
        false -> hiltViewModel<MainScreenScreenViewModelImpl>()
    }
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is MainScreenViewModel.Event.RequestLogin -> {
                    navigator?.navigate(LoginScreenDestination(it.message))
                }
            }
        }
    }

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface[2]
    )

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val items = listOf(
        MainScreenNavigation.Home,
        MainScreenNavigation.Catalog,
        /*MainScreenNavigation.Cart,
        MainScreenNavigation.Favorite,
        MainScreenNavigation.More,*/
    )

    Scaffold(
        content = { innerPadding ->
            DestinationsNavHost(
                navGraph = NavGraphs.mainScreen,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                items.forEach { page ->
                    composable(page.destination) {
                        page.content(
                            this,
                            navigator,
                            snackbarHostState,
                            navController
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            NavigationBar {
                items.forEach { page ->
                    val selected = currentDestination?.route == page.destination.route

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(
                                    if (selected) {
                                        page.selectedIcon
                                    } else {
                                        page.defaultIcon
                                    }
                                ),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(page.label)
                            )
                        },
                        selected = selected,
                        onClick = {
                            navController.navigate(page.destination) {
                                popUpTo(navController.graph.findStartDestination().id) {
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
