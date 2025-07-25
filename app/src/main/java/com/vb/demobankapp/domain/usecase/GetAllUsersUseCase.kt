package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(onResult: (List<User>) -> Unit) {
        return userRepository.getAllUsers(onResult)
    }
} 