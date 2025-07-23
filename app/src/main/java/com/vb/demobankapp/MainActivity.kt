package com.vb.demobankapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.presentation.auth.AuthViewModel
import com.vb.demobankapp.presentation.auth.LoginScreen
import com.vb.demobankapp.presentation.auth.OtpScreen
import com.vb.demobankapp.ui.theme.DemoBankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AuthViewModel = hiltViewModel()
            val context = LocalContext.current
            val activity = context as? Activity

            var isOtpScreen by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf<String?>(null) }
            var isLoginSuccess by remember { mutableStateOf(false) }

            when {
                isLoginSuccess -> {
                    Text(
                        "Giriş Başarılı!",
                        modifier = Modifier.fillMaxSize(),
                        fontSize = 24.sp
                    )
                }
                !isOtpScreen -> {
                    LoginScreen(
                        phoneNumber = viewModel.phoneNumber,
                        onPhoneNumberChange = viewModel::onPhoneNumberChange,
                        onContinueClick = {
                            activity?.let {
                                viewModel.sendOtp(
                                    activity = it,
                                    onSuccess = { isOtpScreen = true },
                                    onError = { errorMessage = it }
                                )
                            }
                        }
                    )
                    errorMessage?.let {
                        Text(it, color = Color.Red)
                    }
                }
                else -> {
                    OtpScreen(
                        otpCode = viewModel.otpCode,
                        onOtpCodeChange = viewModel::onOtpCodeChange,
                        onVerifyClick = {
                            viewModel.verifyOtp(
                                onSuccess = { isLoginSuccess = true },
                                onError = { errorMessage = it }
                            )
                        }
                    )
                    errorMessage?.let {
                        Text(it, color = Color.Red)
                    }
                }
            }
        }
    }
}