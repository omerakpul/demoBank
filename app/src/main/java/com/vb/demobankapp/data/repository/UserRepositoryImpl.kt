package com.vb.demobankapp.data.repository

import android.app.Activity
import com.vb.demobankapp.data.mapper.toDomain
import com.vb.demobankapp.data.mapper.toDto
import com.vb.demobankapp.data.remote.datasource.UserRemoteDataSource
import com.vb.demobankapp.domain.model.User
import com.vb.demobankapp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val ds: UserRemoteDataSource
) : UserRepository {

    override fun addUser(user: User, onResult: (Boolean) -> Unit) {
        ds.addUser(user.toDto(), onResult)
    }

    override fun getUserById(userId: String, onResult: (User?) -> Unit) {
        ds.getUserById(userId) { dto ->
            onResult(dto?.toDomain())
        }
    }

    override fun deleteUser(userId: String, onResult: (Boolean) -> Unit) {
        ds.deleteUser(userId, onResult)
    }

    override fun getAllUsers(onResult: (List<User>) -> Unit) {
        ds.getAllUsers { dtoList ->
            onResult(dtoList.map { it.toDomain() })
        }
    }

    override fun login(phoneNumber: String, activity: Activity, onResult: (Result<String>) -> Unit) {
        ds.login(phoneNumber, activity, onResult)
    }

    override fun validateOtp(phoneNumber: String, otp: String, onResult: (Result<User?>) -> Unit) {
        ds.validateOtp(phoneNumber, otp) { result ->
            result.onSuccess { userDto ->
                if (userDto == null) {
                    // Kullanıcı yok, null döner (register ekranına gidecek)
                    onResult(Result.success(null))
                } else {
                    // Kullanıcı var, domain'e çevir
                    val user = userDto.toDomain()
                    onResult(Result.success(user))
                }
            }.onFailure { error ->
                onResult(Result.failure(error))
            }
        }
    }
}