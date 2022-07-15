package ru.donkids.mobile.ui.screens.main.pages.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.donkids.mobile.R
import ru.donkids.mobile.domain.model.Recent

@Composable
fun History(
    history: List<Recent>,
    onRecent: (Recent) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.recently_watched),
            style = MaterialTheme.typography.titleMedium
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(stringResource(R.string.view_all))
        }
    }
    val recentCount = history.size.coerceAtMost(8)
    if (recentCount == 0) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.empty),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(recentCount) { index ->
            ItemRecent(
                recent = history[index]
            ) {
                onRecent(it)
            }
        }
    }
}
