package com.example.calculatorCurrencyConverter.presentation.currencyConventor

sealed class CurrencyConverterActions {
    object FromCurrencySelect : CurrencyConverterActions()
    object ToCurrencySelect : CurrencyConverterActions()

    data class BottomSheetItemClicked(val value: String) : CurrencyConverterActions()
    data class Number(var number: Int) : CurrencyConverterActions()
    object Clear : CurrencyConverterActions()
    object Delete : CurrencyConverterActions()
    object Decimal : CurrencyConverterActions()
}