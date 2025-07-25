package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, onResult: (User?) -> Unit) {
        return userRepository.getUserById(userId, onResult)
    }
}