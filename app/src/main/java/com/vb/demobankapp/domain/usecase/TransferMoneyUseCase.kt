package com.vb.demobankapp.domain.usecase.AccountUseCases

import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class TransferMoneyUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository
) {
    operator fun invoke(
        fromAccountId: String,
        toIban: String,
        amount: Double,
        onResult: (Boolean) -> Unit
    ) {
        accountRepository.getAccountById(fromAccountId) { fromAccount ->
            if (fromAccount == null || fromAccount.balance < amount) {
                onResult(false)
                return@getAccountById
            }

            accountRepository.getAccountByIban(toIban) { toAccount ->
                if (toAccount == null) {
                    onResult(false)
                    return@getAccountByIban
                }

                val updatedFrom = fromAccount.copy(balance = fromAccount.balance - amount)
                val updatedTo = toAccount.copy(balance = toAccount.balance + amount)

                accountRepository.updateAccount(updatedFrom) { fromSuccess ->
                    if (fromSuccess) {
                        accountRepository.updateAccount(updatedTo) { toSuccess ->
                            onResult(toSuccess)
                        }
                    } else {
                        onResult(false)
                    }
                }
            }
        }
    }
}