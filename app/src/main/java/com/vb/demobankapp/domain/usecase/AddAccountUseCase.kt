package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    suspend operator fun invoke(account: AccountInfo, onResult: (Boolean) -> Unit) {
        return accountRepository.addAccount(account, onResult)
    }
} 