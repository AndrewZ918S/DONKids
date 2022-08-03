package ru.donkids.mobile.ui.core

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@Composable
@NonRestartableComposable
fun DecorSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    darkStatusBar: Boolean = isSystemInDarkTheme(),
    darkNavigationBar: Boolean = isSystemInDarkTheme(),
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    val systemBars = WindowInsets.systemBars.asPaddingValues()
    val context = LocalContext.current
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            insetsController.isAppearanceLightStatusBars = !darkStatusBar
            insetsController.isAppearanceLightNavigationBars = !darkNavigationBar
        }
    }

    Column {
        statusBarColor?.let { color ->
            Spacer(
                Modifier
                    .background(color)
                    .height(systemBars.calculateTopPadding())
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
                    .height(systemBars.calculateBottomPadding())
                    .fillMaxWidth()
            )
        }
    }
}
