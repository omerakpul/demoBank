package com.vb.demobankapp.domain.model

data class AccountInfo(
    val accountId: String,
    val accountName: String,
    val userId: String,
    val iban: String,
    val accountNumber: String,
    val balance: Double,
    val accountType: String
)
