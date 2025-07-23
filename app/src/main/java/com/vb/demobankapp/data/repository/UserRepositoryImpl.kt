package com.vb.demobankapp.data.repository

import com.vb.demobankapp.data.mapper.toDomain
import com.vb.demobankapp.data.mapper.toDto
import com.vb.demobankapp.data.remote.datasource.AccountInfoRemoteDataSource
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: AccountInfoRemoteDataSource
) : AccountInfoRepository {

    override fun addAccount(accountInfo: AccountInfo, onResult: (Boolean) -> Unit) {
        remoteDataSource.addAccount(accountInfo.toDto(), onResult)
    }

    override fun getAccountById(accountId: String, onResult: (AccountInfo?) -> Unit) {
        remoteDataSource.getAccountById(accountId) { dto ->
            onResult(dto?.toDomain())
        }
    }

    override fun deleteAccount(accountId: String, onResult: (Boolean) -> Unit) {
        remoteDataSource.deleteAccount(accountId, onResult)
    }

    override fun getAccountByUserId(userId: String, onResult: (List<AccountInfo>) -> Unit) {
        remoteDataSource.getAccountsByUserId(userId) {dtoList ->
            onResult(dtoList.map { it.toDomain() })
        }
    }
}