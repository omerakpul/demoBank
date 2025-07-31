package com.vb.demobankapp.presentation.ui.account.accounts


sealed class AccountState {
    data object Idle : AccountState()
    data object Loading : AccountState()
    data object Success : AccountState()
    data class Error(val message: String) : AccountState()
}