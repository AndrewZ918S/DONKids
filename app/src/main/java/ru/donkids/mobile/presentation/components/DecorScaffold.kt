package ru.donkids.mobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@ExperimentalMaterial3Api
@Composable
fun DecorScaffold(
    modifier: Modifier = Modifier,
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
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
                            .height(
                                WindowInsets.systemBars
                                    .asPaddingValues()
                                    .calculateBottomPadding()
                            )
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
