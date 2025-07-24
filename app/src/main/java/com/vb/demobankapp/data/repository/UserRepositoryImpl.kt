package com.vb.demobankapp.data.repository

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

}