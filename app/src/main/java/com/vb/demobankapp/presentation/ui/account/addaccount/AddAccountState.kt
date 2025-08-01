package com.vb.demobankapp.presentation.ui.account.addaccount

sealed class AddAccountState {
    data object Idle : AddAccountState()
    data object Loading : AddAccountState()
    data object Success : AddAccountState()
    data class Error(val message: String) : AddAccountState()
}