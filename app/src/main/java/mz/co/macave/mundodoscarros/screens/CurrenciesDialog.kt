package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CurrenciesDialog(
    onDismissRequest: () -> Unit,
    onItemClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.9f),
            shape = CardDefaults.shape,
            colors = CardDefaults.cardColors(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            onClick = {
                onItemClick()
            }
        ) {

            Column {
                Column {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                CurrencyDialogItem(currency = "USD", country = "United States of America")
                CurrencyDialogItem(currency = "MZN", country = "Mozambique")
                CurrencyDialogItem(currency = "BRL", country = "Brazil")
                CurrencyDialogItem(currency = "ZAR", country = "South Africa")
                CurrencyDialogItem(currency = "EUR", country = "Portugal")
                CurrencyDialogItem(currency = "GBP", country = "London")
                CurrencyDialogItem(currency = "KWZ", country = "Angola")
            }
        }

    }

}

@Composable
fun CurrencyDialogItem(currency: String, country: String) {

    val modifier = Modifier
        .padding(horizontal = 8.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            modifier = modifier,
            text = currency,
            style = MaterialTheme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            modifier = modifier,
            text = country,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun DialogPreviews() {
    CurrenciesDialog(
        onItemClick = {},
        onDismissRequest = {}
    )
}