package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CurrenciesDialog(
    onDismissRequest: () -> Unit,
    onItemClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {

        var (selected, onSelected) = remember { mutableStateOf("") }

        Card(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth(),
            shape = AlertDialogDefaults.shape,
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            onClick = {
                onItemClick()
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Currencies",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    )
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    CurrencyDialogItem(selected = true, currency = "USD", country = "United States")
                    CurrencyDialogItem(selected = false, currency = "MZN", country = "Mozambique")
                    CurrencyDialogItem(selected = false, currency = "BRL", country = "Brazil")
                    CurrencyDialogItem(selected = false, currency = "ZAR", country = "South Africa")
                    CurrencyDialogItem(selected = false, currency = "EUR", country = "Portugal")
                    CurrencyDialogItem(selected = false, currency = "GBP", country = "London")
                    CurrencyDialogItem(selected = false, currency = "KWZ", country = "Angola")
                    CurrencyDialogItem(selected = false, currency = "CAD", country = "Canada")
                    CurrencyDialogItem(selected = false, currency = "JPY", country = "Japan")
                    CurrencyDialogItem(selected = false, currency = "YEN", country = "China")
                }
                HorizontalDivider()
                DialogButtons(onDismissRequest)
            }

        }
    }
}

@Composable
fun DialogButtons(onDismissRequest: () -> Unit) {
    Row (
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = { onDismissRequest() }) {
            Text(text = stringResource(id = android.R.string.cancel))
        }

        TextButton(onClick = { onDismissRequest() }) {
            Text(text = stringResource(id = android.R.string.ok))
        }
    }
}

@Composable
fun CurrencyDialogItem(selected: Boolean, currency: String, country: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
            ,
            selected = selected,
            enabled = true,
            onClick = {  }
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = currency,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = country,
                fontSize = 14.sp
            )
        }
    }
}

@Preview
@Composable
fun DialogPreviews() {
    CurrenciesDialog(
        onDismissRequest = {},
        onItemClick = {}
    )
}