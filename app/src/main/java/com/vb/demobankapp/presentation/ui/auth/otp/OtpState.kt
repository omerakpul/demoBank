package com.vb.demobankapp.presentation.ui.auth.otp

sealed class OtpState {
    data object Idle : OtpState()
    data object Loading : OtpState()
    data class Error(val message: String) : OtpState()
    data object Success : OtpState()
    data object UserNotFound : OtpState()
}