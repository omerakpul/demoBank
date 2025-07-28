package com.vb.demobankapp.presentation.ui.auth.login

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Error(val message: String) : LoginState()
    object OtpSent : LoginState()
}