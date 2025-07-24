package com.vb.demobankapp.domain.repository

import com.vb.demobankapp.domain.model.Currency
import com.vb.demobankapp.domain.model.CurrencyConversion

interface CurrencyRepository {
    suspend fun getExchangeRates(base: String, apiKey: String): List<Currency>
    suspend fun convertCurrency(from: String, to: String, amount: Double, apiKey: String): CurrencyConversion
}