package com.vb.demobankapp.presentation.ui.auth.otp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vb.demobankapp.R
import com.vb.demobankapp.presentation.common.components.OtpInputField
import com.vb.demobankapp.presentation.common.ui.theme.BackgroundCream
import com.vb.demobankapp.presentation.common.ui.theme.PrimaryYellow
import com.vb.demobankapp.presentation.common.ui.theme.TextDark
import com.vb.demobankapp.presentation.common.ui.theme.TextPlaceholder

@Composable
fun OtpScreen(
    phoneNumber: String,
    onBackClick: () -> Unit,
    onVerifySuccess: () -> Unit,
    onUserNotFound: (String) -> Unit,
    viewModel: OtpViewModel = hiltViewModel()
) {
    var otpCode by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) {
        when (state) {
            is OtpState.Success -> onVerifySuccess()
            is OtpState.UserNotFound -> onUserNotFound(phoneNumber)
            else -> {}
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
            text = stringResource(R.string.enter_code),
            fontWeight = FontWeight.Bold,
            color = TextDark,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.otp_sent_message, phoneNumber),
            color = TextPlaceholder,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OtpInputField(
            value = otpCode,
            onValueChange = { otpCode = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.validateOtp(phoneNumber, otpCode)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryYellow,
                disabledContainerColor = PrimaryYellow.copy(alpha = 0.5f)
            ),
            enabled = otpCode.length == 6 && state !is OtpState.Loading
        )  {
            if (state is OtpState.Loading) CircularProgressIndicator(color = TextDark)
            else
                Text(
                    text = stringResource(R.string.verify),
                    color = TextDark,
                    fontWeight = FontWeight.Bold
                )
        }

        if (state is OtpState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (state as OtpState.Error).message,
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.resend_code),
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
fun OtpScreenPreview() {
    OtpScreen(
        phoneNumber = "+90 537 971 53 34",
        onBackClick = {},
        onVerifySuccess = {},
        onUserNotFound = {}
    )
}