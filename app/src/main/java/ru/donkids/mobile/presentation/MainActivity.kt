package ru.donkids.mobile.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.donkids.mobile.presentation.screen_login.LoginScreen
import ru.donkids.mobile.presentation.screen_main.MainScreen
import ru.donkids.mobile.presentation.screen_product.ProductScreen
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
                    composable(
                        route = Destinations.PRODUCT.plus("/{id}"),
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        ProductScreen(navController, it.arguments?.getInt("id"))
                    }
                }
            }
        }
    }
}

object Destinations {
    const val LOGIN = "login"
    const val PRODUCT = "main/product"
    const val MAIN = "main"
}

