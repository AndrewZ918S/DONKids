package ru.donkids.mobile.ui.screens.main.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.donkids.mobile.R
import ru.donkids.mobile.ui.theme.get

@Composable
fun SearchBar(
    text: String,
    trailingIcon: @Composable () -> Unit = {},
    onClick: () -> Unit
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
            .clickable {
                onClick()
            }
    ) {
        Row(Modifier.padding(horizontal = 4.dp)) {
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            trailingIcon()
        }
    }
}
