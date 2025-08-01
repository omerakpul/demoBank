package com.vb.demobankapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.domain.usecase.AccountUseCases.GetAccountsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccountsByUserIdUseCase: GetAccountsByUserIdUseCase
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

}