package com.vb.demobankapp.presentation.ui.auth.login

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun sendOtp(phoneNumber: String, activity: Activity) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            loginUseCase(phoneNumber, activity) { result ->
                result.onSuccess { message ->
                    _state.value = LoginState.OtpSent
                }.onFailure { error ->
                    _state.value = LoginState.Error(error.message ?: "OTP gönderilemedi")
                }
            }
        }
    }

    fun resetState() {
        _state.value = LoginState.Idle
    }
}