package com.vb.demobankapp.domain.usecase

import android.app.Activity
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(phoneNumber: String, activity: Activity, onResult: (Result<String>) -> Unit) {
        return repository.login(phoneNumber, activity, onResult)
    }
}