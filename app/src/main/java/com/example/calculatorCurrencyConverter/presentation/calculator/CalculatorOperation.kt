package com.example.calculatorCurrencyConverter.presentation.calculator

sealed class CalculatorOperation(val symbol: String) {
    object Add : CalculatorOperation("+")
    object Sub : CalculatorOperation("-")
    object Div : CalculatorOperation("÷")
    object Mul : CalculatorOperation("×")

}
