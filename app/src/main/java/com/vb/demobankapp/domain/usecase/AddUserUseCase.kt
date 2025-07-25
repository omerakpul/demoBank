package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User, onResult: (Boolean) -> Unit) {
        return userRepository.addUser(user, onResult)
    }
} 