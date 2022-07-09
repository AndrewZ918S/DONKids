package ru.donkids.mobile.presentation.page_catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.presentation.Destinations
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme
import ru.donkids.mobile.presentation.ui.theme.get

@Composable
fun CatalogPage(navController: NavController? = null) {
    val viewModel: CatalogPageViewModel = when (LocalView.current.isInEditMode) {
        true -> object : CatalogPageViewModel() {}
        false -> hiltViewModel<CatalogPageViewModelImpl>()
    }
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.events.collect {
            when (it) {
                is CatalogPageViewModel.Event.RequestLogin -> {
                    navController?.navigate(
                        route = "${Destinations.LOGIN}?msg=${it.message}"
                    )
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                        navController?.navigate("${Destinations.PRODUCT}/-1")

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
            onClick = {

            }
        ) {
            Column {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val pagerState = rememberPagerState()

                    LaunchedEffect(LocalContext.current) {
                        while (true) {
                            yield()
                            delay(5000)

                            var nextPage = pagerState.currentPage + 1
                            if (nextPage >= pagerState.pageCount)
                                nextPage = 0

                            pagerState.animateScrollToPage(nextPage)
                        }
                    }

                    HorizontalPager(
                        count = state.categories.size,
                        state = pagerState
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    role = Role.Image,
                                    onClick = { /*TODO*/ }
                                ),
                            imageModel = DonKidsApi.SITE_URL + state.categories[it].imageLink,
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    if (pagerState.pageCount > 1) {
                        HorizontalPagerIndicator(
                            pagerState = pagerState,
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
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        CatalogPage()
    }
}
