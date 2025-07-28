package com.vb.demobankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vb.demobankapp.presentation.ui.auth.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onBackClick = {
                    // Geri git
                },
                onContinueClick = { phoneNumber ->
                    // OTP ekranına git
                    println("Phone: $phoneNumber - OTP ekranına git")
                }
            )
        }
    }
}