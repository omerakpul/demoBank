package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class ValidateOtpUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        otp: String,
        onResult: (Result<User?>) -> Unit
    ) {
        return userRepository.validateOtp(phoneNumber, otp, onResult)
    }
} 