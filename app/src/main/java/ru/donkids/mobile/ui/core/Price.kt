package ru.donkids.mobile.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.donkids.mobile.ui.theme.DONKidsTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

@Composable
fun Price(
    modifier: Modifier = Modifier,
    price: Float? = null,
    currency: String = "₽",
    color: Color = colorScheme.onSurface,
    priceStyle: TextStyle = typography.titleMedium,
    currencyStyle: TextStyle = typography.titleSmall
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val decimalFormat = DecimalFormat("###,###.##",
            DecimalFormatSymbols(Locale.ENGLISH).apply {
                decimalSeparator = '.'
                groupingSeparator = ' '
            }
        )
        Text(
            text = decimalFormat.format(price ?: 0f),
            color = color,
            style = priceStyle
        )
        Text(
            text = currency,
            color = color,
            style = currencyStyle
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    DONKidsTheme {
        Price()
    }
}
