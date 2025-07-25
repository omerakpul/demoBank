package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    suspend operator fun invoke(accountId: String, onResult: (AccountInfo?) -> Unit) {
        return accountRepository.getAccountById(accountId, onResult)
    }

} 