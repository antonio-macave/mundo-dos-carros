package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TonalToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
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
        modifier = Modifier
            .wrapContentHeight()
    ) {

        val (selectedOption, onOptionSelected) = remember { mutableStateOf(currentCurrency) }

        Column {

            Row(
                modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Text(
                    text = stringResource(id = R.string.pick_the_currency),
                    style = MaterialTheme.typography.bodyLargeEmphasized
                )
            }

            Column {

                CurrenciesButtonGroupBottom(
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
}

@Composable
fun BottomButtons(selectedOption: String, onCancelClickListener: () -> Unit, onOkClickListener: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(onClick = onCancelClickListener) {
            Text(text = stringResource(id = android.R.string.cancel))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                onOkClickListener(selectedOption)
            }
        ) {
            Text(text = stringResource(id = android.R.string.ok))
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CurrenciesButtonGroupBottom(
    selectedOption: String,
    currencies: Map<String, Double>,
    onOptionSelected: (String) -> Unit
) {

    val options = currencies.filter {
        it.key == ("BRL") ||
                it.key == ("USD") ||
                it.key == ("EUR") ||
                it.key == ("MZN")
    }.toList()

    Row(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
    ) {

        val modifiers = List(options.size) {
            Modifier.weight(1f)
        }

        options.forEachIndexed { index, option ->
            TonalToggleButton(
                checked = option.first == selectedOption,
                onCheckedChange = {
                    onOptionSelected(option.first)
                },
                modifier = modifiers[index],
                shapes = when(index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                }
            ) {

                if (option.first == selectedOption) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null
                    )
                }
                Spacer(Modifier.size(ButtonGroupDefaults.ConnectedSpaceBetween))
                Text(
                    text = option.first,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomPreview() {
    BottomButtons("",onCancelClickListener = {}, onOkClickListener = {})
}