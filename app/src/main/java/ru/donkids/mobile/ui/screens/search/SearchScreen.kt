package ru.donkids.mobile.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.core.DecorScaffold
import ru.donkids.mobile.ui.core.InputTextField
import ru.donkids.mobile.ui.screens.destinations.MainScreenDestination
import ru.donkids.mobile.ui.screens.destinations.ProductScreenDestination
import ru.donkids.mobile.ui.screens.search.components.ItemCategory
import ru.donkids.mobile.ui.screens.search.components.ItemProduct
import ru.donkids.mobile.ui.screens.search.components.ItemTitle
import ru.donkids.mobile.ui.screens.search.entity.SearchScreenEvent

@RootNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator? = null
) {
    val viewModel: SearchScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : SearchScreenViewModel() {}
        false -> hiltViewModel<SearchScreenViewModelImpl>()
    }
    val state = viewModel.state

    val topBarColors = TopAppBarDefaults.smallTopAppBarColors()
    val topBarBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarScrollState()
    )

    DecorScaffold(
        modifier = Modifier.nestedScroll(topBarBehavior.nestedScrollConnection),
        statusBarColor = topBarColors
            .containerColor(topBarBehavior.scrollFraction)
            .value,
        navigationBarColor = colorScheme.surface,
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator?.navigateUp()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                title = {
                    InputTextField(
                        value = state.query,
                        onValueChange = { query ->
                            viewModel.onEvent(SearchScreenEvent.QueryChanged(query))
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.search_catalog),
                                style = MaterialTheme.typography.bodyLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = { /* TODO */ }
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge,
                        singleLine = true,
                        requestFocus = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            /* TODO */
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_scanner),
                            contentDescription = stringResource(R.string.scan),
                            tint = colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            Modifier.padding(innerPadding)
        ) {
            items(state.products.size) { index ->
                ItemProduct(state.products[index]) {
                    navigator?.navigate(ProductScreenDestination(id = it.id)) {
                        popUpTo(MainScreenDestination) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            if (state.categories.isNotEmpty()) {
                item {
                    ItemTitle(
                        tite = stringResource(R.string.categories)
                    )
                }
                items(state.categories.size) { index ->
                    ItemCategory(state.categories[index]) {
                        /* TODO */
                    }
                }
            }
            if (state.parents.isNotEmpty()) {
                item {
                    ItemTitle(
                        tite = stringResource(R.string.from_category, state.query)
                    )
                }
                items(state.parents.size) { index ->
                    ItemCategory(state.parents[index]) {
                        /* TODO */
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    SearchScreen()
}