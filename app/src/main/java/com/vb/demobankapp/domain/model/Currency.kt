package com.vb.demobankapp.domain.model

data class Currency(
    val amount: Double,
    val base: String,
    val code: String,
    val rate: Double
)
