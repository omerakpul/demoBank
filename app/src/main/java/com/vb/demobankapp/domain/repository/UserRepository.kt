package com.vb.demobankapp.domain.repository

import com.vb.demobankapp.domain.model.User

interface UserRepository {
    fun addUser(user: User, onResult: (Boolean) -> Unit)
    fun getUserById(userId: String, onResult: (User?) -> Unit)
    fun deleteUser(userId: String, onResult: (Boolean) -> Unit)
    fun getAllUsers(onResult: (List<User>) -> Unit)
}