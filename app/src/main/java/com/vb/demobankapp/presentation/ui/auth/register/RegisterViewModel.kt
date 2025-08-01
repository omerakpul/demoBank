package com.vb.demobankapp.presentation.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.usecase.AddUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val user = User(
                name = name,
                surname = surname,
                phoneNumber = phoneNumber,
                birthDate = birthDate,
                profileImageUrl = "",
                createdAt = currentDate
            )

            addUserUseCase(user) { success ->
                _state.value = if (success) {
                    RegisterState.Success
                } else {
                    RegisterState.Error("Kullanıcı oluşturulamadı.")
                }
            }

        }
    }

    fun resetState(){
        _state.value = RegisterState.Idle

    }
}