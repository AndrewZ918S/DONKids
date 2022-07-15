package ru.donkids.mobile.ui.screens.main.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.donkids.mobile.ui.theme.get

@Composable
fun SearchBar(
    leadingIcon: @Composable () -> Unit,
    text: String,
    trailingIcon: @Composable () -> Unit
) {
    Box(
        Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surface[2],
                shape = CircleShape
            )
    ) {
        Row(Modifier.padding(horizontal = 4.dp)) {
            leadingIcon()
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            trailingIcon()
        }
    }
}
