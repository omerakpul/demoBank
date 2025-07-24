package com.vb.demobankapp.data.remote.api

import com.vb.demobankapp.data.remote.dto.CurrencyConversionDto
import com.vb.demobankapp.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {

    @GET("/api/rates")
    suspend fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("apiKey") apiKey: String
    ): CurrencyDto

    @GET("/api/convert")
    suspend fun convertCurrency(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Double,
        @Query("apiKey") apiKey: String
    ): CurrencyConversionDto

}