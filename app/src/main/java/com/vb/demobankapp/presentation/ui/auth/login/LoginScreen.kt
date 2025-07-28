package com.vb.demobankapp.presentation.ui.auth.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.R
import com.vb.demobankapp.presentation.common.ui.theme.*

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onContinueClick: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as? Activity

    val state by viewModel.state.collectAsState()

    // OTP gönderildiğinde OTP ekranına git
    LaunchedEffect(state) {
        if (state is LoginState.OtpSent) {
            onContinueClick(phoneNumber)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.welcome),
            fontWeight = FontWeight.Bold,
            color = TextDark,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = InputBackground
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.phone_number),
                        color = TextPlaceholder
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
                    unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                    focusedBorderColor = androidx.compose.ui.graphics.Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (activity != null) {
                    viewModel.sendOtp(phoneNumber, activity)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellow,
                disabledContainerColor = PrimaryYellow.copy(alpha = 0.5f)
            ),
            enabled = phoneNumber.isNotBlank() && state !is LoginState.Loading
        ) {
            when (state) {
                is LoginState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = TextDark
                    )
                }
                else -> {
                    Text(
                        text = stringResource(R.string.continue_button),
                        color = TextDark,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Hata mesajı göster
        if (state is LoginState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (state as LoginState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.terms_privacy),
            color = TextPlaceholder,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onBackClick = {},
        onContinueClick = {}
    )
}