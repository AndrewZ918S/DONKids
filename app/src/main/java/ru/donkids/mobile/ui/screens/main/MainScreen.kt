package ru.donkids.mobile.ui.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.launch
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.core.DecorScaffold
import ru.donkids.mobile.ui.screens.NavGraphs
import ru.donkids.mobile.ui.screens.destinations.CatalogPageDestination
import ru.donkids.mobile.ui.screens.destinations.HomePageDestination
import ru.donkids.mobile.ui.screens.destinations.LoginScreenDestination
import ru.donkids.mobile.ui.screens.main.entity.MainScreenNavArgs
import ru.donkids.mobile.ui.screens.main.entity.MainScreenNavigation
import ru.donkids.mobile.ui.theme.DONKidsTheme
import ru.donkids.mobile.ui.theme.get

@RootNavGraph(
    start = true
)
@Destination(
    navArgsDelegate = MainScreenNavArgs::class
)
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
                        launchSingleTop = true
                    }
                }
                is MainScreenViewModel.Event.OpenCatalog -> {
                    navController.navigate(
                        direction = CatalogPageDestination(
                            destinationId = event.id,
                            query = event.query
                        )
                    ) {
                        popUpTo(HomePageDestination) {
                            saveState = true
                        }
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

    if (!LocalView.current.isInEditMode) {
        val activity = LocalContext.current as Activity
        BackHandler {
            activity.finish()
        }
    }

    Box {
        DecorScaffold(
            content = { innerPadding ->
                DestinationsNavHost(
                    navGraph = NavGraphs.mainScreen,
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
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
                                navController.navigate(page.destination.route) {
                                    popUpTo(HomePageDestination) {
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

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.Black.copy(
                            alpha = 0.8f
                        )
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        // block touch events
                    }
            ) {
                CircularProgressIndicator()
                Text(
                    text = stringResource(R.string.updating),
                    color = Color.White.copy(
                        alpha = 0.8f
                    ),
                    textAlign = TextAlign.Center,
                    style = typography.bodyLarge,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        MainScreen()
    }
}
