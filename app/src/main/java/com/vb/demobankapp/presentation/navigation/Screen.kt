package com.vb.demobankapp.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Login : Screen("login_screen")
    data object Otp : Screen("otp_screen")
    data object Register : Screen("register_screen/{phoneNumber}") {
        val arguments = listOf(
            navArgument("phoneNumber") { type = NavType.StringType }
        )
        
        fun createRoute(phoneNumber: String) = "register_screen/$phoneNumber"
    }
    data object Home : Screen("home_screen")
    data object AddAccount : Screen("add_account_screen")
}