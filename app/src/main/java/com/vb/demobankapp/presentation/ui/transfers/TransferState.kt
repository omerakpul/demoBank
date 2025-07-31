package com.vb.demobankapp.presentation.ui.transfers

sealed class TransferState {
    data object Idle : TransferState()
    data object Loading : TransferState()
    data object Success : TransferState()
    data class Error(val message: String) : TransferState()
}