package com.example.calculatorCurrencyConverter.data.remote.dto

data class CurrencyDto(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rates
)
