package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class GetAccountsByUserIdUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    suspend operator fun invoke(userId: String, onResult: (List<AccountInfo>) -> Unit) {
        return accountRepository.getAccountByUserId(userId, onResult)
    }
} 