package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, onResult: (Boolean) -> Unit) {
        return userRepository.deleteUser(userId, onResult)
    }
} 