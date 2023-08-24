package com.example.calculatorCurrencyConverter.presentation.currencyConventor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculatorCurrencyConverter.domain.model.Resource
import com.example.calculatorCurrencyConverter.domain.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterViewModel @Inject constructor(
    private val repository: CurrencyRepository

) : ViewModel() {
    val numberFormat = DecimalFormat("#.00")

    var state by mutableStateOf(CurrencyConventerState())
        private set

    init {
        getCurrencyRatesList()
    }

    fun onAction(action: CurrencyConverterActions) {
        when (action) {
            CurrencyConverterActions.FromCurrencySelect -> {
                state = state.copy(selection = SelectionState.FROM)
            }

            CurrencyConverterActions.ToCurrencySelect -> {
                state = state.copy(selection = SelectionState.TO)
            }

            is CurrencyConverterActions.BottomSheetItemClicked -> {
                if (state.selection == SelectionState.FROM) {
                    state = state.copy(fromCurrencyCode = action.value)
                } else if (state.selection == SelectionState.TO) {
                    state = state.copy(toCurrencyCode = action.value)
                }
                convertCurrency()
            }

            CurrencyConverterActions.Clear -> state = state.copy(
                fromCurrencyValue = "",
                toCurrencyValue = ""
            )

            CurrencyConverterActions.Decimal -> addDecimal()
            CurrencyConverterActions.Delete -> delete()
            is CurrencyConverterActions.Number -> enterNumber(action.number)
        }
    }

    private fun addDecimal() {
        if (state.fromCurrencyValue.contains(".")) return
        state = state.copy(fromCurrencyValue = state.fromCurrencyValue + ".")
    }

    private fun delete() {
        if (state.fromCurrencyValue.isNotBlank()) {
            state = state.copy(
                fromCurrencyValue = state.fromCurrencyValue.dropLast(1)
            )
            convertCurrency()
        }

    }

    private fun enterNumber(number: Int) {
        if (state.fromCurrencyValue.length > MAX_NUM_LENGTH) return


        val updatedFromCurrencyValue = state.fromCurrencyValue + number

        state = state.copy(
            fromCurrencyValue = updatedFromCurrencyValue
        )
        convertCurrency()

    }

    private fun convertCurrency() {
        val fromCurrencyRate = state.currencyRates[state.fromCurrencyCode]?.rate ?: 0.0
        val toCurrencyRate = state.currencyRates[state.toCurrencyCode]?.rate ?: 0.0
        val numberFormat = DecimalFormat("#.00")
        val fromValue = state.fromCurrencyValue.toDoubleOrNull() ?: 0.0
        val toValue = fromValue / fromCurrencyRate * toCurrencyRate

        state = state.copy(
            toCurrencyValue = numberFormat.format(toValue)
        )
    }

    private fun getCurrencyRatesList() {
        viewModelScope.launch {
            repository
                .getCurrencyRatesList()
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {
                            state = state.copy(
                                currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = result.message
                            )
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                currencyRates = result.data?.associateBy { it.code } ?: emptyMap(),
                                error = null
                            )
                        }
                    }
                }
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}