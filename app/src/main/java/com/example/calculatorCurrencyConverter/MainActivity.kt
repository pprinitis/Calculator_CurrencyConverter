package com.example.calculatorCurrencyConverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculatorCurrencyConverter.presentation.MyApp
import com.example.calculatorCurrencyConverter.ui.theme.Calculator2Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator2Theme {

                val buttonSpacing = 20.dp
                val modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)


                MyApp(
                    buttonSpacing = buttonSpacing,
                    modifier = modifier
                )

            }
        }
    }

}
