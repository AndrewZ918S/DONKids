package ru.donkids.mobile.presentation.screen_login.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.donkids.mobile.R

@Composable
fun LoginHeader(
    visible: Boolean = true
) {
    AnimatedVisibility(
        visible = visible
    ) {
        Column {
            Spacer(Modifier.height(32.dp))
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.auth),
                color = MaterialTheme.colorScheme.onSurface,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 36.sp
                )
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}
