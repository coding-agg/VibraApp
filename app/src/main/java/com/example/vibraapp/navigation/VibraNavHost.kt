package com.example.vibraapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vibraapp.ui.auth.LoginScreen
import com.example.vibraapp.ui.auth.RegisterScreen
import com.example.vibraapp.ui.home.HomeScreen
import com.example.vibraapp.ui.splash.SplashScreen
import com.example.vibraapp.viewmodel.AuthViewModel

@Composable
fun VibraNavHost(
    authViewModel: AuthViewModel,
    startDestination: String = Routes.SPLASH
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController = navController, viewModel = authViewModel)
        }
        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                viewModel = authViewModel,onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
