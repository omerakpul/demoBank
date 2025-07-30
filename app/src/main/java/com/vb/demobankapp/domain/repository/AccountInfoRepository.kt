package com.vb.demobankapp.domain.repository

import com.vb.demobankapp.data.remote.dto.AccountInfoDto
import com.vb.demobankapp.domain.model.AccountInfo

interface AccountInfoRepository {
    fun addAccount(accountInfo: AccountInfo, onResult: (Boolean) -> Unit)
    fun getAccountById(accountId: String, onResult: (AccountInfo?) -> Unit)
    fun deleteAccount(accountId: String, onResult: (Boolean) -> Unit)
    fun getAccountByUserId(userId: String, onResult: (Result<List<AccountInfo>>) -> Unit)
}