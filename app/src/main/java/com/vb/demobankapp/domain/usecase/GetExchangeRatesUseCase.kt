package com.vb.demobankapp.domain.usecase

import com.vb.demobankapp.domain.model.Currency
import com.vb.demobankapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetExchangeRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(base: String, apiKey: String): List<Currency> {
        return currencyRepository.getExchangeRates(base, apiKey)
    }
} 