package ru.donkids.mobile.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.donkids.mobile.presentation.screen_login.LoginScreen
import ru.donkids.mobile.presentation.screen_main.MainScreen
import ru.donkids.mobile.presentation.ui.theme.DONKidsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DONKidsTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Destinations.MAIN,
                    modifier = Modifier
                        .imePadding()
                        .systemBarsPadding()
                ) {
                    composable(Destinations.MAIN) {
                        MainScreen(navController)
                    }
                    composable(Destinations.LOGIN) {
                        LoginScreen(navController)
                    }
                }
            }
        }
    }
}

object Destinations {
    const val MAIN = "main"
    const val LOGIN = "login"
}
