package ru.donkids.mobile.ui.screens.main.pages.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.popUpTo
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.core.DecorScaffold
import ru.donkids.mobile.ui.navigation.MainScreenNavGraph
import ru.donkids.mobile.ui.screens.destinations.LoginScreenDestination
import ru.donkids.mobile.ui.screens.destinations.MainScreenDestination
import ru.donkids.mobile.ui.screens.destinations.ProductScreenDestination
import ru.donkids.mobile.ui.screens.destinations.SearchScreenDestination
import ru.donkids.mobile.ui.screens.main.entity.MainScreenNavigation
import ru.donkids.mobile.ui.screens.main.pages.catalog.components.CategoryGrid
import ru.donkids.mobile.ui.screens.main.pages.catalog.components.ItemProduct
import ru.donkids.mobile.ui.screens.main.pages.catalog.entity.CatalogPageEvent
import ru.donkids.mobile.ui.screens.main.pages.catalog.entity.CatalogPageNavArgs
import ru.donkids.mobile.ui.theme.DONKidsTheme

@MainScreenNavGraph
@Destination(
    navArgsDelegate = CatalogPageNavArgs::class
)
@Composable
fun CatalogPage(
    parcel: MainScreenNavigation.Parcel? = null,
) {
    val viewModel: CatalogPageViewModel = when (LocalView.current.isInEditMode) {
        true -> object : CatalogPageViewModel() {}
        false -> hiltViewModel<CatalogPageViewModelImpl>()
    }
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CatalogPageViewModel.Event.RequestLogin -> {
                    parcel?.navigator?.let { navigator ->
                        navigator.navigate(
                            LoginScreenDestination(event.message)
                        ) {
                            launchSingleTop = true
                        }
                    }
                }
                is CatalogPageViewModel.Event.OpenSearch -> {
                    parcel?.navigator?.navigate(
                        SearchScreenDestination(state.query)
                    ) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is CatalogPageViewModel.Event.NavBack -> {
                    parcel?.navController?.navigateUp()
                }
            }
        }
    }

    val topBarColors = TopAppBarDefaults.smallTopAppBarColors()
    val topBarBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarScrollState()
    )

    BackHandler {
        viewModel.onEvent(CatalogPageEvent.NavBack)
    }

    DecorScaffold(
        modifier = Modifier.nestedScroll(topBarBehavior.nestedScrollConnection),
        statusBarColor = topBarColors
            .containerColor(
                scrollFraction = topBarBehavior.scrollFraction
            )
            .value,
        topBar = {
            SmallTopAppBar(
                scrollBehavior = topBarBehavior,
                colors = topBarColors,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(CatalogPageEvent.NavBack)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                title = {
                    Text(
                        text = state.query
                            ?: state.destination?.title
                            ?: stringResource(R.string.categories),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.clickable {
                            viewModel.onEvent(CatalogPageEvent.OpenSearch)
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(CatalogPageEvent.OpenSearch)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_scanner),
                            contentDescription = stringResource(R.string.scan),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.products.isNullOrEmpty()) {
            CategoryGrid(
                categories = state.categories.filter {
                    it.parentId == (state.destination?.id ?: 0)
                },
                onCategory = { product ->
                    viewModel.onEvent(CatalogPageEvent.SelectCategory(product.id))
                },
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(Modifier.padding(innerPadding)) {
                items(state.products.size) { index ->
                    ItemProduct(
                        product = state.products[index]
                    ) { product ->
                        parcel?.navigator?.navigate(
                            ProductScreenDestination(
                                id = product.id
                            )
                        ) {
                            popUpTo(MainScreenDestination)
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        CatalogPage()
    }
}
