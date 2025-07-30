package com.vb.demobankapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.usecase.AddAccountUseCase
import com.vb.demobankapp.domain.usecase.DeleteAccountUseCase
import com.vb.demobankapp.domain.usecase.GetAccountsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccountsByUserIdUseCase: GetAccountsByUserIdUseCase,
    private val addAccountUseCase: AddAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    fun loadAccounts(userId: String) {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            getAccountsByUserIdUseCase(userId) { result ->
                result.onSuccess { accounts ->
                    if (accounts.isEmpty()) {
                        _state.value = HomeState.Empty
                    } else {
                        _state.value = HomeState.Success(accounts)
                    }
                }
            }
        }
    }

    fun addAccount(userId: String) {
        viewModelScope.launch {
            addAccountUseCase(userId) { success ->
                if (success) {
                    loadAccounts(userId)
                }
            }
        }
    }

    fun deleteAccount(accountId: String, userId: String) {
        viewModelScope.launch {
            deleteAccountUseCase(accountId) { success ->
                if (success) {
                    loadAccounts(userId)
                }
            }
        }
    }

}