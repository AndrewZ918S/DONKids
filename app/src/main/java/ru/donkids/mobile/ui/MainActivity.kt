package ru.donkids.mobile.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import ru.donkids.mobile.ui.screens.NavGraphs
import ru.donkids.mobile.ui.theme.DONKidsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DONKidsTheme {
                DestinationsNavHost(
                    modifier = Modifier.imePadding(),
                    navGraph = NavGraphs.root
                )
            }
        }
    }
}
