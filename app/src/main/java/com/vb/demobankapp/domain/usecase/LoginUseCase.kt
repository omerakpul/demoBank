package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String, onResult: (Result<String>) -> Unit) {
        repository.login(phoneNumber, onResult)
    }
}