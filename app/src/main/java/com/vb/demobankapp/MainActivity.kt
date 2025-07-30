package com.vb.demobankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.presentation.ui.account.AddAccountScreen
import com.vb.demobankapp.presentation.ui.auth.login.LoginScreen
import com.vb.demobankapp.presentation.ui.auth.otp.OtpScreen
import com.vb.demobankapp.presentation.ui.auth.register.RegisterScreen
import com.vb.demobankapp.presentation.ui.auth.splash.SplashScreen
import com.vb.demobankapp.presentation.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf("splash") }
            var phoneNumber by remember { mutableStateOf("") }
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            when (currentScreen) {
                "splash" -> SplashScreen(
                    onNavigateToHome = { currentScreen = "home" },
                    onNavigateToLogin = { currentScreen = "login" }
                )
                "login" -> LoginScreen(
                    onBackClick = {},
                    onContinueClick = { phone ->
                        phoneNumber = phone
                        currentScreen = "otp"
                    }
                )
                "otp" -> OtpScreen(
                    phoneNumber = phoneNumber,
                    onBackClick = { currentScreen = "login" },
                    onVerifySuccess = { currentScreen = "home" },
                    onUserNotFound = { currentScreen = "register" }
                )
                "register" -> RegisterScreen(
                    phoneNumber = phoneNumber,
                    onBackClick = { currentScreen = "otp" },
                    onRegisterSuccess = { currentScreen = "home" }
                )
                "home" -> HomeScreen(
                    userId = currentUserId,
                    onAddAccountClick = { currentScreen = "addAccount" },
                    onTransferClick = { /* Transfer ekranına git */ },
                    onCurrencyClick = { /* Currency ekranına git */ },

                )
                "addAccount" -> AddAccountScreen(
                    onBackClick = { currentScreen = "home" },
                    onAddClick = {}
                )
            }
        }
    }
}