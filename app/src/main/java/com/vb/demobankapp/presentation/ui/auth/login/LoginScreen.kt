package com.vb.demobankapp.presentation.ui.auth.login

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.vb.demobankapp.presentation.common.ui.theme.BackgroundCream
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark
import com.vb.demobankapp.presentation.common.ui.theme.TextPlaceholder

@Composable
fun LoginScreen(
    onContinueClick: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var phoneNumber by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity

    val state by viewModel.state.collectAsState()

    fun validateAndFormatPhoneNumber(text: String): String {
        val digitsOnly = text.filter { it.isDigit() }

        if (digitsOnly.startsWith("0")) {
            showError = true
            errorMessage = "Numaranızın başına 0 koymayın"
            return phoneNumber
        }

        if (digitsOnly.length > 10) {
            return phoneNumber
        }
        showError = false
        errorMessage = ""

        return digitsOnly
    }

    LaunchedEffect(state) {
        if (state is LoginState.OtpSent) {
            onContinueClick(phoneNumber)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = stringResource(R.string.welcome),
            fontWeight = FontWeight.Bold,
            color = TextDark,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { newValue ->
                phoneNumber = validateAndFormatPhoneNumber(newValue)
            },
            placeholder = {
                Text(
                    text = "5XX XXX XX XX",
                    color = TextPlaceholder
                )
            },
            modifier = Modifier.width(200.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (showError) MaterialTheme.colorScheme.error else PrimaryYellow,
                unfocusedBorderColor = if (showError) MaterialTheme.colorScheme.error else TextPlaceholder
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )

        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (activity != null) {
                    val fullNumber = "+90" + phoneNumber
                    viewModel.sendOtp(fullNumber, activity)
                }
            },
            enabled = phoneNumber.length == 10 && !showError && state !is LoginState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellow,
                disabledContainerColor = PrimaryYellow.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
        ) {
            if (state is LoginState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = TextDark
                )
            } else {
                Text(
                    text = stringResource(R.string.continue_button),
                    color = TextDark,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        if (state is LoginState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (state as LoginState.Error).message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.terms_privacy),
            color = TextPlaceholder,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onContinueClick = {}
    )
}