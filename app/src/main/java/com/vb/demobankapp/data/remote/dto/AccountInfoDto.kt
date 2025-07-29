package com.vb.demobankapp.data.remote.dto

data class AccountInfoDto(
    val accountId: String? = null,
    val userId: String? = null,
    val iban: String? = null,
    val accountNumber: String? = null,
    val balance: Double? = null,
    val accountType: String? = null
)
