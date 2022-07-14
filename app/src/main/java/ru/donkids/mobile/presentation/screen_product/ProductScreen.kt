package ru.donkids.mobile.presentation.screen_product

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import ru.donkids.mobile.R
import ru.donkids.mobile.presentation.components.InputTextField
import ru.donkids.mobile.presentation.components.Price
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.SystemBarColor
import ru.donkids.mobile.presentation.ui.theme.get

@RootNavGraph
@Destination(
    navArgsDelegate = ProductScreenNavArgs::class
)
@Composable
fun ProductScreen(
    navigator: DestinationsNavigator? = null
) {
    val viewModel: ProductScreenViewModel = when (LocalView.current.isInEditMode) {
        true -> object : ProductScreenViewModel() {}
        false -> hiltViewModel<ProductScreenViewModelImpl>()
    }
    val scope = rememberCoroutineScope()
    val state = viewModel.state

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProductScreenViewModel.Event.Search -> {
                    /* TODO */
                }
                is ProductScreenViewModel.Event.ShowMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
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
            .containerColor(topBarBehavior.scrollFraction)
            .value,
        navigationBarColor = colorScheme.surface[2]
    )

    Scaffold(
        modifier = Modifier.nestedScroll(topBarBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                scrollBehavior = topBarBehavior,
                colors = topBarColors,
                title = {
                    Text(state.category)
                },
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
                    IconButton(
                        onClick = {
                            if (state.isFavorite) {
                                viewModel.onEvent(ProductScreenEvent.GoToFavorites)
                            } else {
                                viewModel.onEvent(ProductScreenEvent.AddToFavorite)
                            }
                        }
                    ) {
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
                }
            )
        },
        content = {
            LazyColumn(
                Modifier.padding(it)
            ) {
                item {
                    Column(
                        Modifier.padding(
                            horizontal = 16.dp
                        )
                    ) {
                        Spacer(Modifier.height(12.dp))
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
                        Spacer(Modifier.height(8.dp))
                        Price(
                            price = state.price,
                            priceStyle = typography.headlineLarge,
                            currencyStyle = typography.headlineMedium
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = stringResource(R.string.specifications),
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                        Row(
                            Modifier.padding(vertical = 16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.product_code),
                                style = typography.bodyMedium,
                                modifier = Modifier.padding(end = 32.dp),
                                color = colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = state.productCode,
                                style = typography.bodyMedium.copy(
                                    textAlign = TextAlign.End
                                ),
                                modifier = Modifier.weight(1f),
                                color = colorScheme.onSurface
                            )
                        }
                    }
                }

                val fields = state.properties.keys.toMutableList()
                items(fields.size) { index ->
                    val key = fields[index]
                    Row(
                        Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = key,
                            style = typography.bodyMedium,
                            modifier = Modifier.padding(end = 32.dp),
                            color = colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = state.properties.getValue(key),
                            style = typography.bodyMedium.copy(
                                textAlign = TextAlign.End
                            ),
                            modifier = Modifier.weight(1f),
                            color = colorScheme.onSurface
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    modifier = Modifier.weight(1f),
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
                    style = typography.bodyMedium.copy(
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(Modifier.width(12.dp))
                AnimatedVisibility(
                    visible = state.isAvailable && state.isInCart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.border(
                            border = IconButtonDefaults.outlinedIconButtonBorder(true),
                            shape = CircleShape
                        )
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ProductScreenEvent.RemoveFromCart)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Remove,
                                contentDescription = stringResource(R.string.remove_from_cart)
                            )
                        }
                        val inCart = state.inCart.toString()
                        val focusManager = LocalFocusManager.current
                        InputTextField(
                            modifier = Modifier.width(32.dp),
                            textStyle = TextStyle(
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            value = TextFieldValue(
                                text = if (inCart != "0") inCart else "",
                                selection = TextRange(
                                    index = inCart.length
                                )
                            ),
                            onValueChange = {
                                viewModel.onEvent(
                                    ProductScreenEvent.EditCart(
                                        value = it.text
                                    )
                                )
                            },
                            onValueCommit = {
                                viewModel.onEvent(ProductScreenEvent.CommitCart)
                            }
                        )
                        IconButton(
                            onClick = {
                                viewModel.onEvent(ProductScreenEvent.AddToCart)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = stringResource(R.string.add_to_cart)
                            )
                        }
                    }
                }
                if (state.isAvailable) {
                    ExtendedFloatingActionButton(
                        elevation = BottomAppBarDefaults.FloatingActionButtonElevation,
                        onClick = {
                            if (state.isInCart) {
                                viewModel.onEvent(ProductScreenEvent.GoToCart)
                            } else {
                                viewModel.onEvent(ProductScreenEvent.AddToCart)
                            }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(
                            stringResource(
                                if (state.isInCart) {
                                    R.string.buy_now
                                } else {
                                    R.string.add_to_cart
                                }
                            )
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
        ProductScreen()
    }
}
