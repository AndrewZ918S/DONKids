package ru.donkids.mobile.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@NonRestartableComposable
fun DecorSurface(
    modifier: Modifier = Modifier,
    shape: Shape = Shapes.None,
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Column {
        statusBarColor?.let { color ->
            Spacer(
                Modifier
                    .background(color)
                    .height(
                        WindowInsets.systemBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
                    .fillMaxWidth()
            )
        }
        Surface(
            modifier = modifier,
            shape = shape,
            color = color,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
            border = border,
            content = content
        )
        navigationBarColor?.let { color ->
            Spacer(
                Modifier
                    .background(color)
                    .height(
                        WindowInsets.systemBars
                            .asPaddingValues()
                            .calculateBottomPadding()
                    )
                    .fillMaxWidth()
            )
        }
    }
}
