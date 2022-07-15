package ru.donkids.mobile.presentation.page_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.data.remote.DonKidsApi
import ru.donkids.mobile.domain.model.Recent
import ru.donkids.mobile.presentation.components.Price

@Composable
fun ItemRecent(
    recent: Recent,
    onClick: (Recent) -> Unit
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick(recent)
            }
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
                style = MaterialTheme.typography.bodyMedium,
                color = if (recent.isAvailable) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                maxLines = 2
            )
            Price(
                price = recent.price,
                color = if (recent.isAvailable) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}
