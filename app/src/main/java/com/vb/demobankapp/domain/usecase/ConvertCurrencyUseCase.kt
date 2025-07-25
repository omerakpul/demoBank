package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.Currency
import com.vb.demobankapp.domain.model.CurrencyConversion
import com.vb.demobankapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class ConvertCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(
        from: String,
        to: String,
        amount: Double,
        apiKey: String
    ): CurrencyConversion {
        return currencyRepository.convertCurrency(from, to, amount, apiKey)
    }
} 