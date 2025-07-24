package com.vb.demobankapp.data.repository

import com.vb.demobankapp.data.mapper.toCurrencyList
import com.vb.demobankapp.data.mapper.toDomain
import com.vb.demobankapp.data.remote.datasource.CurrencyRemoteDataSource
import com.vb.demobankapp.domain.model.Currency
import com.vb.demobankapp.domain.model.CurrencyConversion
import com.vb.demobankapp.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currecyRemoteDataSource: CurrencyRemoteDataSource
) : CurrencyRepository {

    override suspend fun getExchangeRates(base:String, apikey:String) : List<Currency> {
        val dto = currecyRemoteDataSource.getExchangeRates(base, apikey)
        return dto.toCurrencyList()
    }

    override suspend fun convertCurrency(from: String, to: String, amount: Double, apiKey: String): CurrencyConversion {
        val dto = currecyRemoteDataSource.convertCurrency(from, to, amount, apiKey)
        return dto.toDomain()
    }
}