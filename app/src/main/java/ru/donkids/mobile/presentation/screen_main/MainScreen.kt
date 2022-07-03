package ru.donkids.mobile.presentation.screen_main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.donkids.mobile.presentation.Destinations
import ru.donkids.mobile.presentation.page_home.HomePage
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor
import ru.donkids.mobile.presentation.ui.theme.surfaceTone

@Composable
fun MainScreen(navController: NavController? = null) {
    val viewModel: MainScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : MainScreenViewModel() {}
        false -> hiltViewModel<MainScreenScreenViewModelImpl>()
    }
    val state = viewModel.state

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surfaceTone(2)
    )

    BackHandler(
        enabled = state.selectedPage != 0,
        onBack = {
            viewModel.onEvent(MainScreenEvent.NavigationItemSelected(0))
        }
    )

    LaunchedEffect(Unit) {
        viewModel.onEvent(MainScreenEvent.CheckLogin)

        viewModel.events.collect {
            when(it) {
                is MainScreenViewModel.Event.RequestLogin -> {
                    navController?.navigate(Destinations.LOGIN)
                }
            }
        }
    }

    Scaffold(
        content = { innerPadding ->
            Surface(Modifier.padding(innerPadding)) {
                when (state.selectedPage) {
                    0 -> HomePage(navController)
                }
            }
        },
        bottomBar = {
            NavigationBar {
                state.pages.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(
                                    if (index == state.selectedPage)
                                        item.iconSelected
                                    else
                                        item.iconDefault
                                ),
                                null
                            )
                        },
                        label = {
                            Text(stringResource(item.label))
                            colorScheme.surface
                        },
                        selected = index == state.selectedPage,
                        onClick = {
                            viewModel.onEvent(MainScreenEvent.NavigationItemSelected(index))
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
