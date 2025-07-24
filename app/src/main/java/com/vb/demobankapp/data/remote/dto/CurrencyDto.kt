package com.vb.demobankapp.data.remote.dto

data class CurrencyDto(
    val amount: Double? = null,
    val base: String? = null,
    val rates: Map<String, Double>? = null
)