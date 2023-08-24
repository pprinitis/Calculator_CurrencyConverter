package com.example.calculatorCurrencyConverter.presentation.calculator


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorActions) {
        when (action) {
            is CalculatorActions.Number -> enterNumber(action.number)
            is CalculatorActions.Decimal -> enterDecimal()
            is CalculatorActions.Clear -> state = CalculatorState()
            is CalculatorActions.Delete -> performdelete()
            is CalculatorActions.Calculate -> calculateResult()
            is CalculatorActions.Operation -> enterOperation(action.operation)
        }
    }


    private fun enterOperation(op: CalculatorOperation) {
        if (state.num1.isNotBlank()) {
            state = state.copy(operation = op)
        }
    }

    private fun calculateResult() {
        val number1 = state.num1.toDoubleOrNull()
        val number2 = state.num2.toDoubleOrNull()


        if (number1 == null || number2 == null) {
            return
        }
        val calculation = when (state.operation) {
            is CalculatorOperation.Add -> number1 + number2
            is CalculatorOperation.Sub -> number1 - number2
            is CalculatorOperation.Mul -> number1 * number2
            is CalculatorOperation.Div -> number1 / number2
            null -> return
        }

        printResult(calculation)

    }

    private fun performdelete() {
        when {
            state.num2.isNotBlank() -> state = state.copy(
                num2 = state.num2.dropLast(1)
            )

            state.operation != null -> state = state.copy(
                operation = null
            )

            state.num1.isNotBlank() -> state = state.copy(
                num1 = state.num1.dropLast(1)
            )
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.num1.length > MAX_NUM_LENGTH) {
                return
            }

            state = state.copy(num1 = state.num1 + number)
            return
        }
        if (state.num2.length > MAX_NUM_LENGTH) {
            return
        }
        state = state.copy(num2 = state.num2 + number)

    }

    private fun enterDecimal() {
        if (state.operation == null && !state.num1.contains(".")) {
            if (state.num1.isBlank()) {
                enterNumber(0)
            }
            state = state.copy(num1 = state.num1 + ".")
            return
        }
        if (!state.num2.contains(".")) {
            if (state.num2.isBlank()) {
                enterNumber(0)
            }
            state = state.copy(num2 = state.num2 + ".")
            return
        }
    }

    private fun printResult(result: Double) {
        val formattedResult = if (result.isWholeNumber()) {
            result.toInt().toString().take(15)
        } else {
            result.toString().take(15)
        }
        state = state.copy(
            num1 = formattedResult,
            operation = null,
            num2 = ""
        )

    }

    private fun Double.isWholeNumber(): Boolean {
        return this % 1 == 0.0
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}