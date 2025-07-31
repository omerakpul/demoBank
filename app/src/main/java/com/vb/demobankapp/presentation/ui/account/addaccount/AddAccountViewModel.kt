package com.vb.demobankapp.presentation.ui.account.addaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.domain.usecase.AccountUseCases.AddAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddAccountState>(AddAccountState.Idle)
    val state: StateFlow<AddAccountState> = _state

    fun addAccount(accountName: String) {
        viewModelScope.launch {
            _state.value = AddAccountState.Loading
            val userId = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""
            addAccountUseCase(userId, accountName) { success ->
                if (success)
                    _state.value = AddAccountState.Success
                else
                    _state.value = AddAccountState.Error("Error adding account")
            }
        }
    }

    fun resetState() {
        _state.value = AddAccountState.Idle
    }
}