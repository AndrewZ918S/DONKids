package ru.donkids.mobile.presentation.screen_product

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.components.Price
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor
import ru.donkids.mobile.presentation.ui.theme.get

@Composable
fun ProductScreen(navController: NavController? = null) {
    val viewModel: ProductScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : ProductScreenViewModel() {}
        false -> hiltViewModel<ProductScreenViewModelImpl>()
    }
    val context = LocalContext.current
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is ProductScreenViewModel.Event.Search -> {
                    /* TODO */
                }
            }
        }
    }

    SystemBarColor(
        statusBarColor = colorScheme.surface,
        navigationBarColor = colorScheme.surface[2]
    )

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(state.category)
                },
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
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(ProductScreenEvent.OpenSearch)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
        content = {
            Column(
                Modifier
                    .padding(it)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    )
            ) {
                ElevatedCard(
                    Modifier.height(280.dp)
                ) {
                    if (!LocalView.current.isInEditMode) {
                        GlideImage(
                            imageModel = state.imageLink,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = state.title,
                    style = typography.titleLarge,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Row {
                    Text(
                        text = state.productCode,
                        style = typography.labelLarge
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = state.vendorCode,
                        style = typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        if (state.isAvailable) {
                            R.string.available
                        } else {
                            R.string.not_available
                        }
                    ),
                    color = if (state.isAvailable) {
                        colorScheme.primary
                    } else {
                        colorScheme.onSurfaceVariant
                    },
                    style = typography.bodySmall
                )
                Spacer(Modifier.height(2.dp))
                Price(
                    price = state.price,
                    color = if (state.isAvailable) {
                        colorScheme.onSurface
                    } else {
                        colorScheme.onSurfaceVariant
                    },
                    priceStyle = typography.headlineLarge,
                    currencyStyle = typography.headlineMedium
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                icons = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            painter = painterResource(
                                if (state.inCart) {
                                    R.drawable.ic_cart_filled
                                } else {
                                    R.drawable.ic_cart_outline
                                }
                            ),
                            contentDescription = stringResource(
                                if (state.inCart) {
                                    R.string.view_in_cart
                                } else {
                                    R.string.add_to_cart
                                }
                            ),
                            tint = if (state.inCart) {
                                colorScheme.primary
                            } else {
                                colorScheme.onSurfaceVariant
                            }
                        )
                    }
                    IconButton(onClick = {
                        /* TODO */
                    }) {
                        Icon(
                            painter = painterResource(
                                if (state.isFavorite) {
                                    R.drawable.ic_favorite_filled
                                } else {
                                    R.drawable.ic_favorite_outline
                                }
                            ),
                            contentDescription = stringResource(
                                if (state.isFavorite) {
                                    R.string.view_in_favorites
                                } else {
                                    R.string.add_to_favorites
                                }
                            ),
                            tint = if (state.isFavorite) {
                                colorScheme.tertiary
                            } else {
                                colorScheme.onSurfaceVariant
                            }
                        )
                    }
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        elevation = BottomAppBarDefaults.FloatingActionButtonElevation,
                        onClick = {
                            /*TODO*/
                        }
                    ) {
                        Text(stringResource(R.string.buy_now))
                    }
                }
            )
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
