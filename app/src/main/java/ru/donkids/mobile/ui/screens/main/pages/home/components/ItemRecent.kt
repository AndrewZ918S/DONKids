package ru.donkids.mobile.ui.screens.main.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.domain.model.Recent
import ru.donkids.mobile.ui.core.Price

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
            ElevatedCard(
                Modifier.alpha(
                    if (recent.isAvailable) {
                        1.0f
                    } else {
                        0.5f
                    }
                )
            ) {
                GlideImage(
                    imageModel = recent.getImageLink(),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.background(Color.White)
                )
            }
            Text(
                text = recent.title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (recent.isAvailable) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                overflow = TextOverflow.Ellipsis,
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
