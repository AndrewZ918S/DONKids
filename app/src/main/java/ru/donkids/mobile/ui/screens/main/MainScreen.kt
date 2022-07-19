package ru.donkids.mobile.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.core.DecorScaffold
import ru.donkids.mobile.ui.screens.NavGraphs
import ru.donkids.mobile.ui.screens.destinations.LoginScreenDestination
import ru.donkids.mobile.ui.screens.main.entity.MainScreenNavigation
import ru.donkids.mobile.ui.theme.DONKidsTheme
import ru.donkids.mobile.ui.theme.get

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
    val state = viewModel.state

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scope = rememberCoroutineScope()
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

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is MainScreenViewModel.Event.RequestLogin -> {
                    navigator?.navigate(LoginScreenDestination(event.message)) {
                        navigator.popBackStack()
                        launchSingleTop = true
                    }
                }
                is MainScreenViewModel.Event.ShowMessage -> {
                    event.message?.let { message ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
        }
    }

    DecorScaffold(
        content = { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                DestinationsNavHost(
                    navGraph = NavGraphs.mainScreen,
                    navController = navController
                ) {
                    items.forEach { page ->
                        composable(page.destination) {
                            page.content(
                                parcel = MainScreenNavigation.Parcel(
                                    navigator = navigator,
                                    snackbarState = snackbarHostState,
                                    navController = navController
                                )
                            )
                        }
                    }
                }
                if (state.isLoading) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Black.copy(
                                    alpha = if (state.isLoading) {
                                        0.8f
                                    } else {
                                        0f
                                    }
                                )
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                // prevent content touches
                            }
                    ) {
                        CircularProgressIndicator()
                        Text(
                            text = stringResource(R.string.updating),
                            textAlign = TextAlign.Center,
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        navigationBarColor = colorScheme.surface[2],
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
                            if (state.isLoading) {
                                return@NavigationBarItem
                            }

                            navController.navigate(page.destination.route) {
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
