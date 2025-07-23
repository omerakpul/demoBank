package com.vb.demobankapp.data.remote.dto

data class CurrencyConversionDto(
    val amount: Double? = null,
    val from: String? = null,
    val to: String? = null,
    val result: Double? = null
)
