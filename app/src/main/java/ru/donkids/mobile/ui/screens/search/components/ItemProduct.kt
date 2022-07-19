package ru.donkids.mobile.ui.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.domain.model.Product

@Composable
fun ItemProduct(
    product: Product,
    onClick: (Product) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick(product)
            }
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
        ) {
            GlideImage(
                imageModel = product.getThumbLink(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.background(Color.White)
            )
        }
        Text(
            text = product.abbreviation,
            style = typography.bodyMedium,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        )
    }
}