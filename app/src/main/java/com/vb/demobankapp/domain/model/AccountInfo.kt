package com.vb.demobankapp.domain.model

data class AccountInfo(
    val accountId: String,
    val userId: String,
    val iban: String,
    val accountNumber: String
)
