package com.example.calculatorCurrencyConverter.presentation.currencyConventor

import com.example.calculatorCurrencyConverter.domain.model.CurrencyRate

data class CurrencyConventerState(
    val fromCurrencyCode: String = "EUR",
    val toCurrencyCode: String = "USD",
    val fromCurrencyValue: String = "",
    val toCurrencyValue: String = "",
    val selection: SelectionState = SelectionState.FROM,

    val currencyRates: Map<String, CurrencyRate> = emptyMap(),
    val error: String? = null

)

enum class SelectionState {
    FROM,
    TO
}