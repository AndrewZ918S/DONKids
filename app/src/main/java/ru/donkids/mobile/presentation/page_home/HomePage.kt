package ru.donkids.mobile.presentation.page_home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.presentation.components.DecorSurface
import ru.donkids.mobile.presentation.components.Price
import ru.donkids.mobile.presentation.components.openCustomTab
import ru.donkids.mobile.presentation.destinations.ProductScreenDestination
import ru.donkids.mobile.presentation.ui.navigation.MainScreenNavGraph
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.get

@MainScreenNavGraph(
    start = true
)
@Destination
@Composable
fun HomePage(
    navigator: DestinationsNavigator? = null,
    snackbarHostState: SnackbarHostState? = null
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
                        navigator?.navigate(
                            ProductScreenDestination(
                                id = productId
                            )
                        )
                    } ?: event.productCode?.let { productCode ->
                        navigator?.navigate(
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
                        snackbarHostState?.showSnackbar(event.message)
                    }
                }
            }
        }
    }

    DecorSurface(
        statusBarColor = colorScheme.surface
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Box(
                Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                    .background(
                        color = colorScheme.surface[2],
                        shape = CircleShape
                    )
            ) {
                Row(Modifier.padding(horizontal = 4.dp)) {
                    IconButton(
                        onClick = {
                            navigator?.navigate(
                                ProductScreenDestination(
                                    code = "1517"
                                )
                            )
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search),
                            tint = colorScheme.onSurface
                        )
                    }
                    Text(
                        text = stringResource(R.string.search_catalog),
                        color = colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_scanner),
                            contentDescription = stringResource(R.string.scan),
                            tint = colorScheme.onSurface
                        )
                    }
                }
            }
            ElevatedCard(
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = { /* TODO */ }
            ) {
                Column {
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val banners = state.banners
                        if (banners.isNotEmpty()) {
                            val pagerState = rememberPagerState()

                            LaunchedEffect(Unit) {
                                val start = Int.MAX_VALUE / 2

                                pagerState.scrollToPage(
                                    page = start - start % banners.size
                                )

                                while (pagerState.currentPage + 1 < Int.MAX_VALUE) {
                                    delay(5000)
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }

                            HorizontalPager(
                                count = Int.MAX_VALUE,
                                state = pagerState
                            ) {
                                val banner = banners[it % banners.size]
                                GlideImage(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            },
                                            indication = rememberRipple(),
                                            role = Role.Image,
                                            onClick = {
                                                viewModel.onEvent(HomePageEvent.OpenBanner(banner))
                                            }
                                        ),
                                    imageModel = DonKidsApi.SITE_URL + banner.image,
                                    contentScale = ContentScale.FillWidth
                                )
                            }
                            HorizontalPagerIndicator(
                                pagerState = pagerState,
                                pageCount = banners.size,
                                pageIndexMapping = {
                                    it % banners.size
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        color = Color.Black.copy(alpha = 0.2f),
                                        shape = CircleShape
                                    )
                                    .padding(4.dp),
                                activeColor = colorScheme.primary,
                                inactiveColor = Color.White,
                                indicatorHeight = 4.dp,
                                indicatorWidth = 4.dp,
                                spacing = 8.dp
                            )
                        }
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Icon(
                            modifier = Modifier
                                .padding(16.dp)
                                .background(
                                    color = colorScheme.secondaryContainer,
                                    shape = CircleShape
                                )
                                .padding(8.dp)
                                .size(24.dp),
                            painter = painterResource(R.drawable.ic_new_releases),
                            tint = colorScheme.onSecondaryContainer,
                            contentDescription = null
                        )
                        Column(
                            Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = stringResource(R.string.new_title),
                                style = typography.titleMedium
                            )
                            Text(
                                text = stringResource(R.string.new_body),
                                style = typography.bodyMedium
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.recently_watched),
                    style = typography.titleMedium
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(stringResource(R.string.view_all))
                }
            }
            val recentCount = state.history.size.coerceAtMost(8)
            if (recentCount == 0) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(R.string.empty),
                        style = typography.bodyMedium
                    )
                }
            }
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(recentCount) { index ->
                    val recent = state.history[index]
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                role = Role.Image,
                                onClick = {
                                    viewModel.onEvent(HomePageEvent.OpenRecent(recent))
                                }
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(128.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ElevatedCard {
                                GlideImage(
                                    imageModel = DonKidsApi.SITE_URL + recent.imageLink,
                                    contentScale = ContentScale.FillWidth,
                                    alpha = if (recent.isAvailable) {
                                        1.0f
                                    } else {
                                        0.5f
                                    }
                                )
                            }
                            Text(
                                text = recent.abbreviation,
                                style = typography.bodyMedium,
                                color = if (recent.isAvailable) {
                                    colorScheme.onSurface
                                } else {
                                    colorScheme.onSurfaceVariant
                                },
                                maxLines = 2
                            )
                            Price(
                                price = recent.price,
                                color = if (recent.isAvailable) {
                                    colorScheme.onSurface
                                } else {
                                    colorScheme.onSurfaceVariant
                                }
                            )
                        }
                    }
                }
            }
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
