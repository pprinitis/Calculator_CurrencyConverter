package com.example.calculatorCurrencyConverter.data.repository

import android.util.Log
import com.example.calculatorCurrencyConverter.data.local.CurrencyRateDao
import com.example.calculatorCurrencyConverter.data.local.entity.toCurrencyRate
import com.example.calculatorCurrencyConverter.data.local.entity.toCurrencyRateEntity
import com.example.calculatorCurrencyConverter.data.remote.CurrencyAPI
import com.example.calculatorCurrencyConverter.data.remote.dto.toCurrencyRate
import com.example.calculatorCurrencyConverter.domain.model.CurrencyRate
import com.example.calculatorCurrencyConverter.domain.model.Resource
import com.example.calculatorCurrencyConverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class CurrencyRepositoryImplementation(
    private val api: CurrencyAPI,
    private val dao: CurrencyRateDao

) : CurrencyRepository {
    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow {
        Log.d("newRates", "1")
        val localCurrencyRates = getLocalCurrencyRates()
        Log.d("newRates", "2")
        emit(Resource.Success(localCurrencyRates))
        Log.d("newRates", "3")

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resource.Success(newRates))
        } catch (e: IOException) {
            Log.d("Server responce", "${e.message}")
            emit(
                Resource.Error(
                    message = "${e.message}",
                    data = localCurrencyRates
                )
            )
        } catch (e: Exception) {
            Log.d("Server responce2", "${e.message}")
            emit(
                Resource.Error(
                    message = "Something went wrong ${e.message}",
                    data = localCurrencyRates

                )
            )
        }
    }

    private suspend fun getLocalCurrencyRates(): List<CurrencyRate> {
        return dao.getAllCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate> {
        val response = api.getLatestRates()
        Log.d("Server responce", "${response}")
        return response.toCurrencyRate()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>) {
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }

}