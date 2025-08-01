package com.vb.demobankapp.presentation.ui.account.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.usecase.AccountUseCases.DeleteAccountUseCase
import com.vb.demobankapp.domain.usecase.AccountUseCases.UpdateAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val updateAccountUseCase: UpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AccountState>(AccountState.Idle)
    val state: StateFlow<AccountState> = _state

    fun updateAccountName(accountId: String, newName: String) {
        viewModelScope.launch {
            _state.value = AccountState.Loading
            updateAccountUseCase(accountId, newName) { success ->
                _state.value = if (success) AccountState.Success else AccountState.Error("Güncelleme başarısız")
            }
        }
    }

    fun deleteAccount(accountId: String) {
        viewModelScope.launch {
            _state.value = AccountState.Loading
            deleteAccountUseCase(accountId) { success ->
                _state.value = if (success) AccountState.Success else AccountState.Error("Silme başarısız")
            }
        }
    }
}