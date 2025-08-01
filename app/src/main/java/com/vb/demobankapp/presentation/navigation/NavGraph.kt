package com.vb.demobankapp.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.ui.account.accounts.AccountScreen
import com.vb.demobankapp.presentation.ui.account.addaccount.AddAccountScreen
import com.vb.demobankapp.presentation.ui.auth.login.LoginScreen
import com.vb.demobankapp.presentation.ui.auth.otp.OtpScreen
import com.vb.demobankapp.presentation.ui.auth.register.RegisterScreen
import com.vb.demobankapp.presentation.ui.auth.splash.SplashScreen
import com.vb.demobankapp.presentation.ui.home.HomeScreen
import com.vb.demobankapp.presentation.ui.transfers.TransferScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "splash_screen"
) {
    // Seçili account'ı burada tutuyaoruz
    val selectedAccount = remember { mutableStateOf<AccountInfo?>(null) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("splash_screen") {
            SplashScreen(
                onNavigateToHome = { navController.navigate(Screen.Home.route) },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable("login_screen") {
            LoginScreen(
                onContinueClick = { phoneNumber ->
                    navController.navigate(Screen.Otp.createRoute(phoneNumber))
                }
            )
        }

        composable(
            route = Screen.Otp.route,
            arguments = Screen.Otp.arguments
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            OtpScreen(
                phoneNumber = phoneNumber,
                onBackClick = { navController.popBackStack() },
                onVerifySuccess = { navController.navigate(Screen.Home.route) },
                onUserNotFound = { navController.navigate(Screen.Register.createRoute(phoneNumber)) }
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
                onRegisterSuccess = { navController.navigate(Screen.Home.route) }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onAddAccountClick = { navController.navigate(Screen.AddAccount.route) },
                onTransferClick = { navController.navigate(Screen.Transfer.route) },
                onCurrencyClick = { /* Currency ekranına git */ },
                onAccountClick = { account ->
                    selectedAccount.value = account
                    navController.navigate(Screen.Account.route)
                }
            )
        }

        composable(Screen.AddAccount.route) {
            AddAccountScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Account.route) {
            val account = selectedAccount.value
            if (account != null) {
                AccountScreen(
                    account = account,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text("Hesap bulunamadı.")
            }
        }

        composable(Screen.Transfer.route) {
            TransferScreen(
                onBackClick = { navController.popBackStack() },
                onTransferClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }
}