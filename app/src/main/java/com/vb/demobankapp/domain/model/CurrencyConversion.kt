package com.vb.demobankapp.domain.model

data class CurrencyConversion(
    val amount: Double,
    val from: String,
    val to: String,
    val result: Double
)
