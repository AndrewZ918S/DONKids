package ru.donkids.mobile.ui.screens.main.pages.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.skydoves.landscapist.glide.GlideImage
import ru.donkids.mobile.R
import ru.donkids.mobile.domain.model.Product
import ru.donkids.mobile.ui.core.Price

@Composable
fun ItemProduct(
    product: Product,
    onClick: (Product) -> Unit
) {
    ConstraintLayout(
        Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick(product)
            }
    ) {
        val (image, title, code, vendor, price, favorite, action, spacer) = createRefs()

        val alpha = if (product.isAvailable) {
            1.0f
        } else {
            0.5f
        }

        ElevatedCard(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .alpha(alpha)
                .size(100.dp)
                .constrainAs(image) {
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    top.linkTo(parent.top, margin = 8.dp)
                }
        ) {
            GlideImage(
                imageModel = product.getThumbLink(),
                contentScale = ContentScale.Fit,
                modifier = Modifier.background(Color.White)
            )
        }
        Text(
            modifier = Modifier.constrainAs(title) {
                width = Dimension.fillToConstraints
                end.linkTo(favorite.start)
                start.linkTo(image.end, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            },
            text = product.title,
            style = typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 4
        )
        Text(
            modifier = Modifier.constrainAs(code) {
                start.linkTo(title.start)
                top.linkTo(title.bottom, margin = 4.dp)
            },
            text = product.code,
            style = typography.labelMedium
        )
        Text(
            modifier = Modifier.constrainAs(vendor) {
                start.linkTo(code.end, margin = 4.dp)
                top.linkTo(code.top)
            },
            text = product.vendorCode,
            style = typography.bodySmall
        )
        Price(
            modifier = Modifier.constrainAs(price) {
                bottom.linkTo(image.bottom)
                start.linkTo(title.start)
                top.linkTo(code.bottom)
            },
            price = product.price,
            color = if (product.isAvailable) {
                colorScheme.onSurface
            } else {
                colorScheme.onSurfaceVariant
            }
        )
        IconButton(
            modifier = Modifier.constrainAs(favorite) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            onClick = {
                /* TODO */
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_favorite_outline),
                contentDescription = stringResource(R.string.add_to_favorites),
                tint = colorScheme.onSurfaceVariant
            )
        }
        Spacer(
            modifier = Modifier.constrainAs(spacer) {
                height = Dimension.fillToConstraints
                bottom.linkTo(action.top)
                top.linkTo(title.bottom)
            }
        )
        Box(
            Modifier.constrainAs(action) {
                bottom.linkTo(parent.bottom, margin = 8.dp)
                end.linkTo(parent.end, margin = 8.dp)
                top.linkTo(spacer.bottom)
            }
        ) {
            if (product.isAvailable) {
                OutlinedButton(
                    onClick = {
                        /* TODO */
                    }
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        style = typography.labelLarge
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.not_available),
                    color = colorScheme.onSurfaceVariant,
                    style = typography.bodySmall
                )
            }
        }
    }
}
