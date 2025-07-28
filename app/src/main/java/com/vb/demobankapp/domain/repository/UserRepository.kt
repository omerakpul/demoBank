package com.vb.demobankapp.domain.repository

import com.vb.demobankapp.domain.model.User

interface UserRepository {
    fun addUser(user: User, onResult: (Boolean) -> Unit)
    fun getUserById(userId: String, onResult: (User?) -> Unit)
    fun deleteUser(userId: String, onResult: (Boolean) -> Unit)
    fun getAllUsers(onResult: (List<User>) -> Unit)
    fun login(phoneNumber: String, onResult: (Result<String>) -> Unit) // OTP gönder
    fun validateOtp(phoneNumber: String, otp: String, onResult: (Result<User?>) -> Unit) // OTP doğrula - User? olarak değişti
}