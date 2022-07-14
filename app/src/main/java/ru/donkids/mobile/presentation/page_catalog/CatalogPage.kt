package ru.donkids.mobile.presentation.page_catalog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.presentation.destinations.LoginScreenDestination
import ru.donkids.mobile.presentation.ui.navigation.MainScreenNavGraph
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor

@MainScreenNavGraph
@Destination
@Composable
fun CatalogPage(
    navigator: DestinationsNavigator? = null,
    snackbarHostState: SnackbarHostState? = null,
    navController: NavController? = null
) {
    val viewModel: CatalogPageViewModel = when (LocalView.current.isInEditMode) {
        true -> object : CatalogPageViewModel() {}
        false -> hiltViewModel<CatalogPageViewModelImpl>()
    }
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is CatalogPageViewModel.Event.RequestLogin -> {
                    navigator?.navigate(
                        LoginScreenDestination(it.message)
                    )
                }
            }
        }
    }

    val topBarColors = TopAppBarDefaults.smallTopAppBarColors()
    val topBarBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = rememberTopAppBarScrollState()
    )

    SystemBarColor(
        statusBarColor = topBarColors
            .containerColor(scrollFraction = topBarBehavior.scrollFraction)
            .value
    )

    Scaffold(
        modifier = Modifier.nestedScroll(topBarBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                scrollBehavior = topBarBehavior,
                colors = topBarColors,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController?.navigateUp()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.go_back)
                        )
                    }
                },
                title = {
                    Text(text = "Categories")
                }
            )
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(innerPadding)
            ) {
                items(state.categories.size) { index ->
                    val category = state.categories[index]

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        ElevatedCard(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(48.dp)
                        ) {
                            GlideImage(
                                imageModel = DonKidsApi.SITE_URL + category.imageLink
                            )
                        }
                        Text(
                            text = category.abbreviation,
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        CatalogPage()
    }
}
