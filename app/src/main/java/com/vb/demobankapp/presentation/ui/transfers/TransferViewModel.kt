package com.vb.demobankapp.presentation.ui.transfers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.usecase.AccountUseCases.GetAccountsByUserIdUseCase
import com.vb.demobankapp.domain.usecase.AccountUseCases.TransferMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    private val getAccountsByUserIdUseCase: GetAccountsByUserIdUseCase,
    private val transferMoneyUseCase: TransferMoneyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<TransferState>(TransferState.Idle)
    val state: StateFlow<TransferState> = _state

    private val _selectedAccount = MutableStateFlow<AccountInfo?>(null)
    val selectedAccount: StateFlow<AccountInfo?> = _selectedAccount

    private val _availableAccounts = MutableStateFlow<List<AccountInfo>>(emptyList())
    val availableAccounts: StateFlow<List<AccountInfo>> = _availableAccounts

    private val _recipientIban = MutableStateFlow("")
    val recipientIban: StateFlow<String> = _recipientIban

    private val _transferAmount = MutableStateFlow("")
    val transferAmount: StateFlow<String> = _transferAmount

    init {
        loadUserAccounts()
    }

    fun loadUserAccounts() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
            getAccountsByUserIdUseCase(userId) { result ->
                result.onSuccess { accounts ->
                    _availableAccounts.value = accounts
                }
            }
        }
    }

    fun selectAccount(account: AccountInfo) {
        _selectedAccount.value = account
    }

    fun updateRecipientIban(iban: String) {
        val cleanIban = iban.replace(" ", "")
        val ibanWithoutPrefix = if (cleanIban.startsWith("TR38")) cleanIban.substring(4) else cleanIban
        val numbersOnly = ibanWithoutPrefix.filter { it.isDigit() }.take(22)
        _recipientIban.value = numbersOnly
    }

    fun updateTransferAmount(amount: String) {
        val filteredAmount = amount.filter { it.isDigit() || it == ',' || it == '.' }
        _transferAmount.value = filteredAmount
    }

    fun validateAmount(): Boolean {
        return try {
            val amountValue = _transferAmount.value.replace(",", ".").toDouble()
            val accountBalance = _selectedAccount.value?.balance ?: 0.0
            amountValue > 0 && amountValue <= accountBalance
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun transferMoney() {
        val account = _selectedAccount.value
        val iban = _recipientIban.value
        val amountStr = _transferAmount.value

        if (account == null) {
            _state.value = TransferState.Error("Lütfen bir hesap seçin")
            return
        }

        if (iban.length != 22) {
            _state.value = TransferState.Error("Geçersiz IBAN")
            return
        }

        // Kullanıcının tüm hesaplarında bu IBAN var mı kontrol et
        val fullIban = "TR38$iban"
        val isOwnAccount = _availableAccounts.value.any { it.iban == fullIban }

        if (isOwnAccount) {
            _state.value = TransferState.Error("Kendi hesabınıza transfer yapamazsınız")
            return
        }

        if (!validateAmount()) {
            _state.value = TransferState.Error("Geçersiz transfer miktarı")
            return
        }

        val amount = amountStr.replace(",", ".").toDouble()

        viewModelScope.launch {
            _state.value = TransferState.Loading

            transferMoneyUseCase(
                fromAccountId = account.accountId,
                toIban = fullIban,
                amount = amount
            ) { success ->
                if (success) {
                    _state.value = TransferState.Success
                    // Formu temizle
                    _recipientIban.value = ""
                    _transferAmount.value = ""
                } else {
                    _state.value = TransferState.Error("Transfer işlemi başarısız")
                }
            }
        }
    }

    fun resetState() {
        _state.value = TransferState.Idle
    }
}