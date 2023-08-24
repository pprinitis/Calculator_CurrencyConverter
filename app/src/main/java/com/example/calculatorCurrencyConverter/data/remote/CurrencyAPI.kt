package com.example.calculatorCurrencyConverter.data.remote

import com.example.calculatorCurrencyConverter.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("access_key") apiKey: String = API_KEY
    ): CurrencyDto

    companion object {
        const val API_KEY = "f91bd09457e6733763f053fbcec7b519"
        const val BASE_URL = "http://data.fixer.io/api/"
    }
}