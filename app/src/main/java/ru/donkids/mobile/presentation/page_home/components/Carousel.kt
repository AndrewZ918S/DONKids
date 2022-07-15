package ru.donkids.mobile.presentation.page_home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import ru.donkids.mobile.R
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.Banner

@Composable
fun Carousel(
    banners: List<Banner>,
    onBanner: (Banner) -> Unit,
    action: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = action
    ) {
        Column {
            Box(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.BottomCenter
            ) {
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
                                        onBanner(banner)
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
                        activeColor = MaterialTheme.colorScheme.primary,
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
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape
                        )
                        .padding(8.dp)
                        .size(24.dp),
                    painter = painterResource(R.drawable.ic_new_releases),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    contentDescription = null
                )
                Column(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = stringResource(R.string.new_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = stringResource(R.string.new_body),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}