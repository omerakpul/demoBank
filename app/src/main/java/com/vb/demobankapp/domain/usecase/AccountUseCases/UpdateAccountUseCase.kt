package com.vb.demobankapp.domain.usecase.AccountUseCases

import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    operator fun invoke(accountId: String, newName: String, onResult: (Boolean) -> Unit) {
        accountRepository.getAccountById(accountId) { existingAccount ->
            if (existingAccount != null) {
                val updatedAccount = existingAccount.copy(accountName = newName)
                accountRepository.updateAccount(updatedAccount, onResult)
            } else {
                onResult(false)
            }
        }
    }
}