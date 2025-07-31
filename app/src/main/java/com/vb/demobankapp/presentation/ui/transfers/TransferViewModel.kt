package com.vb.demobankapp.presentation.ui.transfers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.presentation.ui.transfers.TransferState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<TransferState>(TransferState.Idle)
    val state: StateFlow<TransferState> = _state

    private val _selectedAccount = MutableStateFlow<AccountInfo?>(null)
    val selectedAccount: StateFlow<AccountInfo?> = _selectedAccount

    fun selectAccount(account: AccountInfo) {
        _selectedAccount.value = account
    }

    fun transferMoney(
        fromAccount: AccountInfo,
        toIban: String,
        recipientName: String,
        recipientSurname: String,
        amount: Double
    ) {
        viewModelScope.launch {
            _state.value = TransferState.Loading
        }
    }
}