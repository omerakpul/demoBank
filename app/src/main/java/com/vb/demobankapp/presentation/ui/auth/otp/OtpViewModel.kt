package com.vb.demobankapp.presentation.ui.auth.otp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.usecase.ValidateOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val validateOtpUseCase: ValidateOtpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<OtpState>(OtpState.Idle)
    val state: StateFlow<OtpState> = _state

    fun validateOtp(phoneNumber: String, otp: String) {
        viewModelScope.launch {
            _state.value = OtpState.Loading

            validateOtpUseCase(phoneNumber, otp) { result ->
                result.onSuccess { user ->
                    if(user != null)
                    _state.value = OtpState.Success
                    else
                        _state.value = OtpState.UserNotFound
                }.onFailure { error ->
                    _state.value = OtpState.Error(error.message ?: "OTP doğrulanamadı")
                }
            }
        }
    }

    fun resetState() {
        _state.value = OtpState.Idle
    }
}