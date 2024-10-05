package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mz.co.macave.mundodoscarros.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesBottomSheet(
    currentCurrency: String,
    rates: MutableMap<String, Double>,
    state: SheetState,
    onCurrencySelected: (String) -> Unit,
    onDismissListener: () -> Unit
) {
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest =  onDismissListener,
    ) {

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(currentCurrency) }

        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {

            Row(
                modifier = Modifier
                .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.pick_the_currency),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                CurrencyItems(
                    selectedOption = selectedOption,
                    onOptionSelected = onOptionSelected,
                    currencies = rates
                )
            }

            MainCurrencies(
                selectedOption = selectedOption,
                currencies = rates,
                onOptionSelected = onOptionSelected
            )

            BottomButtons(
                selectedOption = selectedOption,
                onCancelClickListener = {
                         onDismissListener.invoke()
                },
                onOkClickListener = onCurrencySelected
            )
        }
    }
}

@Composable
fun CurrencyItems(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    currencies: MutableMap<String, Double>
) {
    val options = currencies.keys.toList()

    listOf(Color.Blue, Color.Magenta)
    options.forEach { text ->
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(6.dp))
                .border(
                    width = if (text == selectedOption) 1.dp else 0.dp,
                    color = if (text == selectedOption) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                )
                .background(
                    color = if (text == selectedOption) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                )
                .selectable(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            RadioButton(selected = (text == selectedOption) , onClick = { onOptionSelected(text) })
            Text(text = text)
        }
    }
}

@Composable
fun BottomButtons(selectedOption: String, onCancelClickListener: () -> Unit, onOkClickListener: (String) -> Unit) {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        OutlinedButton(onClick = onCancelClickListener) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = android.R.string.cancel)
            )
            Text(text = stringResource(id = android.R.string.cancel))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(onClick = {
            onOkClickListener(selectedOption)
        }) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(id = android.R.string.ok)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = stringResource(id = android.R.string.ok))
        }
    }
}


@Composable
fun MainCurrencies(
    selectedOption: String,
    currencies: MutableMap<String, Double>,
    onOptionSelected: (String) -> Unit
) {
    val mainCurrencies = currencies.filter {
        it.key.contains("BRL") ||
                it.key.contains("USD") ||
                it.key.contains("EUR") ||
                it.key.contains("MZN")
    }

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        mainCurrencies.keys.forEach { text ->
            FilterChip(
                enabled = true,
                selected = (text == selectedOption),
                onClick = { onOptionSelected(text) },
                label = { Text(text = text) },
                leadingIcon = { if (selectedOption == text)  Icon(imageVector = Icons.Default.Check, contentDescription = null) }
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}


@Preview
@Composable
fun BottomPreview() {
    BottomButtons("",onCancelClickListener = {}, onOkClickListener = {})
}