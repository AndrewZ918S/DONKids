package ru.donkids.mobile.ui.screens.main.pages.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.launch
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.core.DecorSurface
import ru.donkids.mobile.ui.core.openCustomTab
import ru.donkids.mobile.ui.navigation.MainScreenNavGraph
import ru.donkids.mobile.ui.screens.destinations.CatalogPageDestination
import ru.donkids.mobile.ui.screens.destinations.HomePageDestination
import ru.donkids.mobile.ui.screens.destinations.ProductScreenDestination
import ru.donkids.mobile.ui.screens.destinations.SearchScreenDestination
import ru.donkids.mobile.ui.screens.main.entity.MainScreenNavigation
import ru.donkids.mobile.ui.screens.main.pages.home.components.Carousel
import ru.donkids.mobile.ui.screens.main.pages.home.components.History
import ru.donkids.mobile.ui.screens.main.pages.home.components.SearchBar
import ru.donkids.mobile.ui.screens.main.pages.home.entity.HomePageEvent
import ru.donkids.mobile.ui.theme.DONKidsTheme

@MainScreenNavGraph(
    start = true
)
@Destination
@Composable
fun HomePage(
    parcel: MainScreenNavigation.Parcel? = null
) {
    val viewModel: HomePageViewModel = when (LocalView.current.isInEditMode) {
        true -> object : HomePageViewModel() {}
        false -> hiltViewModel<HomePageViewModelImpl>()
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomePageEvent.Refresh)
        viewModel.events.collect { event ->
            when (event) {
                is HomePageViewModel.Event.OpenProduct -> {
                    event.productId?.let { productId ->
                        parcel?.navigator?.navigate(
                            ProductScreenDestination(
                                id = productId
                            )
                        )
                    } ?: event.productCode?.let { productCode ->
                        parcel?.navigator?.navigate(
                            ProductScreenDestination(
                                code = productCode
                            )
                        )
                    }
                }
                is HomePageViewModel.Event.OpenUrl -> {
                    openCustomTab(context, event.url)
                }
                is HomePageViewModel.Event.ShowMessage -> {
                    scope.launch {
                        parcel?.snackbarState?.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    DecorSurface(
        statusBarColor = colorScheme.surface
    ) {
        Column(Modifier.verticalScroll(scrollState)) {
            SearchBar(
                text = stringResource(R.string.search_catalog),
                trailingIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_scanner),
                            contentDescription = stringResource(R.string.scan),
                            tint = colorScheme.onSurface
                        )
                    }
                }
            ) {
                parcel?.navigator?.navigate(
                    SearchScreenDestination()
                ) {
                    launchSingleTop = true
                }
            }
            Carousel(
                banners = state.banners,
                onBanner = { banner ->
                    viewModel.onEvent(HomePageEvent.OpenBanner(banner))
                }
            ) {
                parcel?.navController?.let { navController ->
                    navController.navigate(
                        direction = CatalogPageDestination(
                            destinationId = 1024764 // New toys
                        )
                    ) {
                        popUpTo(HomePageDestination) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            History(
                history = state.history,
                onRecent = { recent ->
                    viewModel.onEvent(HomePageEvent.OpenRecent(recent))
                }
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        HomePage()
    }
}
