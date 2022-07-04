package ru.donkids.mobile.presentation.screen_product

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor

@Composable
fun ProductScreen(navController: NavController? = null, id: Int? = null) {
    val viewModel: ProductScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : ProductScreenViewModel() {}
        false -> hiltViewModel<ProductScreenViewModelImpl>()
    }
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ProductScreenViewModel.Event.Close -> {
                    navController?.navigateUp()
                }
                is ProductScreenViewModel.Event.Search -> {

                }
            }
        }
    }

    BackHandler(
        onBack = {
            viewModel.onEvent(ProductScreenEvent.BackPressed)
        }
    )

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { state.category?.abbreviation },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(ProductScreenEvent.BackPressed)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favorite_outline),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
        content = {
            Spacer(Modifier.padding(it))
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        ProductScreen()
    }
}
