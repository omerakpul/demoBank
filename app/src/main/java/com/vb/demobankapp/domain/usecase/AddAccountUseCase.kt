package com.vb.demobankapp.domain.usecase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.vb.demobankapp.domain.model.AccountInfo
import com.vb.demobankapp.domain.repository.AccountInfoRepository
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountRepository: AccountInfoRepository,
    private val auth: FirebaseAuth
) {
    suspend operator fun invoke(userId: String, accountName: String, onResult: (Boolean) -> Unit) {

        val iban = generateUniqueIban()
        val accountNumber = generateUniqueAccountNumber()

        val account = AccountInfo(
            accountId = "",
            userId = userId,
            iban = iban,
            accountNumber = accountNumber,
            balance = 5000.0,
            accountType = "TRY",
            accountName = accountName
        )
        return accountRepository.addAccount(account, onResult)
    }

    fun generateUniqueIban(): String {
        // TR + timestamp son 8 hanesi + random 4 hane
        val timestamp = System.currentTimeMillis().toString().takeLast(8)
        val random = (1000..9999).random()
        return "TR38${timestamp}${random}"
    }

    fun generateUniqueAccountNumber(): String {
        // timestamp son 8 hane + random 8 hane
        val timestamp = System.currentTimeMillis().toString().takeLast(8)
        val random = (10000000..99999999).random()
        return "${timestamp}${random}"
    }
} 