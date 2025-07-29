package com.vb.demobankapp.presentation.ui.auth.register

sealed class RegisterState {
    data object Idle : RegisterState()
    data object Loading : RegisterState()
    data class Error(val message: String) : RegisterState()
    data object Success : RegisterState()
}