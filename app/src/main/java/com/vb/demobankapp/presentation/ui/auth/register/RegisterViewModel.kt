package com.vb.demobankapp.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state

    fun addUser(name: String, surname: String, birthDate: String, phoneNumber: String) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading

            val user = User(
                name = name,
                surname = surname,
                phoneNumber = phoneNumber,
                birthDate = birthDate,
                profileImageUrl = "",
                createdAt = System.currentTimeMillis()
            )

            addUserUseCase(user) { success ->
                _state.value = if (success) {
                    RegisterState.Success
                } else {
                    RegisterState.Error("An error occured while adding user")
                }
            }

        }
    }

    fun resetState(){
        _state.value = RegisterState.Idle

    }
}