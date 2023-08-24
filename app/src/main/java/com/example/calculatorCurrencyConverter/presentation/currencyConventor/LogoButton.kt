package com.example.calculatorCurrencyConverter.presentation.currencyConventor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun LogoButton(
    modifier: Modifier,
    logo: @Composable () -> Unit,

    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .then(modifier)
    ) {
        logo()
    }
}