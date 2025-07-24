package com.vb.demobankapp.data.remote.datasource

import com.vb.demobankapp.data.remote.api.CurrencyApiService
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyApiService: CurrencyApiService
) {
    suspend fun getExchangeRates(base: String, apiKey: String) =
        currencyApiService.getExchangeRates(base, apiKey)

    suspend fun convertCurrency(from: String, to: String, amount: Double, apiKey: String) =
        currencyApiService.convertCurrency(from, to, amount, apiKey)

}