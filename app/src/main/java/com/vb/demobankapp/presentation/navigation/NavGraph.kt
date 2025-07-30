package com.vb.demobankapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.presentation.ui.account.addaccount.AddAccountScreen
import com.vb.demobankapp.presentation.ui.auth.login.LoginScreen
import com.vb.demobankapp.presentation.ui.auth.otp.OtpScreen
import com.vb.demobankapp.presentation.ui.auth.register.RegisterScreen
import com.vb.demobankapp.presentation.ui.auth.splash.SplashScreen
import com.vb.demobankapp.presentation.ui.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onContinueClick = { phoneNumber ->
                    navController.navigate(Screen.Otp.route)
                }
            )
        }

        composable(Screen.Otp.route) {
            OtpScreen(
                phoneNumber = "", // Şimdilik boş
                onBackClick = { navController.popBackStack() },
                onVerifySuccess = {
                    navController.navigate(Screen.Home.route)
                },
                onUserNotFound = { phoneNumber ->
                    navController.navigate(Screen.Register.createRoute(phoneNumber))
                }
            )
        }

        composable(
            route = Screen.Register.route,
            arguments = Screen.Register.arguments
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            RegisterScreen(
                phoneNumber = phoneNumber,
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onAddAccountClick = {
                    navController.navigate(Screen.AddAccount.route)
                },
                onTransferClick = { /* Transfer ekranına git */ },
                onCurrencyClick = { /* Currency ekranına git */ },
                onLogoutClick = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.AddAccount.route) {
            AddAccountScreen(
                onBackClick = { navController.popBackStack() },
                onAddClick = { /* ViewModel kendi işini yapıyor */ }
            )
        }
    }
}