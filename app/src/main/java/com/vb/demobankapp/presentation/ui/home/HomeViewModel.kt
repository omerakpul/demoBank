package com.vb.demobankapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.usecase.GetAccountByIdUseCase
import com.vb.demobankapp.domain.usecase.GetAccountsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccountsByUserIdUseCase: GetAccountsByUserIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    suspend fun loadAccounts(userId: String) {
        _state.value = HomeState(isLoading = true)

        getAccountsByUserIdUseCase(userId) { accounts ->
            _state.value = HomeState(
                isLoading = false,
                accounts = accounts
            )
        }
    }
}