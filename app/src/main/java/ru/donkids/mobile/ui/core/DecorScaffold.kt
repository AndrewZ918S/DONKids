package ru.donkids.mobile.ui.core

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@ExperimentalMaterial3Api
@Composable
fun DecorScaffold(
    modifier: Modifier = Modifier,
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    darkStatusBar: Boolean = isSystemInDarkTheme(),
    darkNavigationBar: Boolean = isSystemInDarkTheme(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
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

    Scaffold(
        modifier = modifier,
        topBar = {
            Column {
                statusBarColor?.let { color ->
                    Spacer(
                        Modifier
                            .background(color)
                            .height(systemBars.calculateTopPadding())
                            .fillMaxWidth()
                    )
                }
                topBar()
            }
        },
        bottomBar = {
            Column {
                bottomBar()
                navigationBarColor?.let { color ->
                    Spacer(
                        Modifier
                            .background(color)
                            .height(systemBars.calculateBottomPadding())
                            .fillMaxWidth()
                    )
                }
            }
        },
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        content = content
    )
}
