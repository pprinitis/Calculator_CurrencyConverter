package com.example.calculatorCurrencyConverter.domain.repository

import com.example.calculatorCurrencyConverter.domain.model.CurrencyRate
import com.example.calculatorCurrencyConverter.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>>

}