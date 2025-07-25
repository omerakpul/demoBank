package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    suspend operator fun invoke(accountId: String, onResult: (Boolean) -> Unit) {
        return accountRepository.deleteAccount(accountId, onResult)
    }

} 