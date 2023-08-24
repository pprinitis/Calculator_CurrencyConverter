package com.example.calculatorCurrencyConverter.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorCurrencyConverter.R
import com.example.calculatorCurrencyConverter.presentation.calculator.CalculatorActions
import com.example.calculatorCurrencyConverter.presentation.calculator.CalculatorButton
import com.example.calculatorCurrencyConverter.presentation.calculator.CalculatorOperation
import com.example.calculatorCurrencyConverter.presentation.calculator.CalculatorState
import com.example.calculatorCurrencyConverter.presentation.calculator.CalculatorViewModel
import com.example.calculatorCurrencyConverter.presentation.currencyConventor.BottomSheetContent
import com.example.calculatorCurrencyConverter.presentation.currencyConventor.CurrencyConventerState
import com.example.calculatorCurrencyConverter.presentation.currencyConventor.CurrencyConverterActions
import com.example.calculatorCurrencyConverter.presentation.currencyConventor.CurrencyConverterViewModel
import com.example.calculatorCurrencyConverter.presentation.currencyConventor.LogoButton
import com.example.calculatorCurrencyConverter.ui.theme.LightGrayColor
import com.example.calculatorCurrencyConverter.ui.theme.greenColor
import com.example.calculatorCurrencyConverter.ui.theme.orangeColor
import kotlinx.coroutines.launch


@Composable
fun MyApp(buttonSpacing: Dp, modifier: Modifier) {
    val (showUI1, setShowUI1) = remember { mutableStateOf(true) }

    if (showUI1) {
        val viewModel = viewModel<CalculatorViewModel>()
        val state = viewModel.state

        Calculator(
            state = state,
            buttonSpacing = buttonSpacing,
            modifier = modifier,
            onAction = viewModel::onAction
        ) { setShowUI1(false) }
    } else {
        val viewModel = viewModel<CurrencyConverterViewModel>()
        val state = viewModel.state
        CurrencyConverterUI(
            state = state,
            buttonSpacing = buttonSpacing,
            modifier = modifier,
            onAction = viewModel::onAction
        ) { setShowUI1(true) }
    }
}


@Composable
fun Calculator(
    state: CalculatorState,
    buttonSpacing: Dp = 16.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onAction: (CalculatorActions) -> Unit,
    onSwitchClicked: () -> Unit
) {

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                text = state.num1 + (state.operation?.symbol ?: "") + state.num2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                fontWeight = FontWeight.Light,
                fontSize = 80.sp,
                color = Color.White,
                maxLines = 2
            )
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "C",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = orangeColor

                ) {
                    onAction(CalculatorActions.Clear)
                }
                CalculatorButton(
                    symbol = "\u232B",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = orangeColor

                ) {
                    onAction(CalculatorActions.Delete)
                }
                CalculatorButton(
                    symbol = "$",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.Green,

                    ) {
                    onSwitchClicked()
                }
                CalculatorButton(
                    symbol = "\u00F7",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = greenColor,


                    ) {
                    onAction(CalculatorActions.Operation(CalculatorOperation.Div))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "7",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(7))
                }
                CalculatorButton(
                    symbol = "8",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(8))
                }
                CalculatorButton(
                    symbol = "9",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(9))
                }
                CalculatorButton(
                    symbol = "\u00D7",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = greenColor,


                    ) {
                    onAction(CalculatorActions.Operation(CalculatorOperation.Mul))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "4",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(4))
                }
                CalculatorButton(
                    symbol = "5",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(5))
                }
                CalculatorButton(
                    symbol = "6",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(6))
                }
                CalculatorButton(
                    symbol = "\u002B",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = greenColor,


                    ) {
                    onAction(CalculatorActions.Operation(CalculatorOperation.Add))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "1",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(1))
                }
                CalculatorButton(
                    symbol = "2",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(2))
                }
                CalculatorButton(
                    symbol = "3",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(3))
                }
                CalculatorButton(
                    symbol = "\u2212",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = greenColor,


                    ) {
                    onAction(CalculatorActions.Operation(CalculatorOperation.Sub))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = ".",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Decimal)
                }
                CalculatorButton(
                    symbol = "0",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CalculatorActions.Number(0))
                }
                CalculatorButton(
                    symbol = "\u003D",
                    modifier = Modifier
                        .background(greenColor)
                        .aspectRatio(2f)
                        .weight(2f),
                    symbolColor = Color.White,
                ) {
                    onAction(CalculatorActions.Calculate)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterUI(
    state: CurrencyConventerState,
    buttonSpacing: Dp = 16.dp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onAction: (CurrencyConverterActions) -> Unit,
    onSwitchClicked: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var shouldBottomSheetShow by remember { mutableStateOf(false) }
    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { shouldBottomSheetShow = false },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = "Select Currency",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            },
            content = {
                BottomSheetContent(
                    onItemClicked = { currencyCode ->
                        onAction(CurrencyConverterActions.BottomSheetItemClicked(currencyCode))
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) shouldBottomSheetShow = false
                        }
                    },
                    currenciesList = state.currencyRates.values.toList()
                )
            }
        )
    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Title and Back Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            LightGrayColor,
                            shape = CircleShape
                        ) // Set the background color and shape
                ) {
                    IconButton(
                        onClick = { onSwitchClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White // Set the icon color
                        )
                    }
                }


                Text(
                    text = "Currency Converter",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    maxLines = 1
                )

            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    CurrencyRow(
                        modifier = Modifier.fillMaxWidth(),
                        currencyCode = state.fromCurrencyCode,
                        currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                        onDropDownIconClicked = {
                            shouldBottomSheetShow = true
                            onAction(CurrencyConverterActions.FromCurrencySelect)
                        }
                    )
                    Text(
                        text = state.fromCurrencyValue,
                        fontSize = 40.sp

                    )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    CurrencyRow(
                        modifier = Modifier.fillMaxWidth(),
                        currencyCode = state.toCurrencyCode,
                        currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                        onDropDownIconClicked = {
                            shouldBottomSheetShow = true
                            onAction(CurrencyConverterActions.ToCurrencySelect)
                        }
                    )
                    Text(
                        text = state.toCurrencyValue,
                        fontSize = 40.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "7",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(7))
                }
                CalculatorButton(
                    symbol = "8",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(8))
                }
                CalculatorButton(
                    symbol = "9",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(9))
                }
                CalculatorButton(
                    symbol = "C",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = orangeColor

                ) {
                    onAction(CurrencyConverterActions.Clear)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "4",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(4))
                }
                CalculatorButton(
                    symbol = "5",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(5))
                }
                CalculatorButton(
                    symbol = "6",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(6))
                }
                CalculatorButton(
                    symbol = "\u232B",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = greenColor

                ) {
                    onAction(CurrencyConverterActions.Delete)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                CalculatorButton(
                    symbol = "1",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(1))
                }
                CalculatorButton(
                    symbol = "2",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(2))
                }
                CalculatorButton(
                    symbol = "3",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(3))
                }
                CalculatorButton(
                    symbol = ".",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(1f)
                        .weight(1f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Decimal)
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {

                CalculatorButton(
                    symbol = "0",
                    modifier = Modifier
                        .background(LightGrayColor)
                        .aspectRatio(2f)
                        .weight(2f),
                    symbolColor = Color.White
                ) {
                    onAction(CurrencyConverterActions.Number(0))
                }

                LogoButton(
                    modifier = Modifier
                        .background(LightGrayColor, shape = CircleShape)
                        .aspectRatio(2f)
                        .weight(2f),
                    logo = {
                        val svgLogo = MySvgLogo()
                        Icon(painter = svgLogo, contentDescription = "Logo")
                    }
                )


            }
        }
    }
}

@Composable
fun MySvgLogo(): Painter {
    // Load your SVG logo as a Painter
    return painterResource(id = R.drawable.desquared_logo_big) // Replace with your SVG resource
}

@Composable
private fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currencyCode, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = onDropDownIconClicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open available currencies"
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = currencyName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
