package com.vb.demobankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.presentation.ui.account.AddAccountScreen
import com.vb.demobankapp.presentation.ui.auth.login.LoginScreen
import com.vb.demobankapp.presentation.ui.auth.otp.OtpScreen
import com.vb.demobankapp.presentation.ui.auth.register.RegisterScreen
import com.vb.demobankapp.presentation.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

// MainActivity.kt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf("login") } // Başlangıç login
            var phoneNumber by remember { mutableStateOf("") }

            // Anlık kullanıcı ID'si
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            when (currentScreen) {
                "login" -> LoginScreen(
                    onBackClick = { /* Geri git */ },
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
                    onCurrencyClick = { /* Currency ekranına git */ }
                )
                "addAccount" -> AddAccountScreen(
                    onBackClick = { currentScreen = "home" },
                    onAddClick = { /* ViewModel kendi işini yapıyor */ }
                )
            }
        }
    }
}