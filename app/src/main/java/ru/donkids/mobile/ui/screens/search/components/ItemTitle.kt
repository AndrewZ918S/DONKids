package ru.donkids.mobile.ui.screens.search.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ItemTitle(
    tite: String
) {
    Text(
        text = tite,
        style = typography.titleMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 12.dp
        )
    )
}