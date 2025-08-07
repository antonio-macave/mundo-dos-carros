package mz.co.macave.mundodoscarros.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CurrenciesSplitButton(
    currentCurrency: String,
) {
    var checked by remember { mutableStateOf(false) }
    SplitButtonLayout(
        spacing = 8.dp,
        leadingButton = {
            SplitButtonDefaults.LeadingButton(
                onClick = { checked = !checked },
            ) {
                Icon(
                    imageVector = Icons.Filled.CurrencyExchange,
                    modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize),
                    contentDescription = null
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = currentCurrency)
            }
        },
        trailingButton = {
            SplitButtonDefaults.TrailingButton(
                checked = checked,
                onCheckedChange = {  checked = !checked  }
            ) {
                val rotation: Float by animateFloatAsState(
                    targetValue = if (checked) 180f else 0f,
                    label = "Rotação do botão"
                )

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    modifier = Modifier
                        .size(SplitButtonDefaults.TrailingIconSize)
                        .graphicsLayer(
                            rotationZ = rotation
                        ),
                    contentDescription = null
                )
            }
        },
    )
    DropdownMenu(
        expanded = checked,
        onDismissRequest = {  checked = !checked  }
    ) {
        DropdownMenuItem(
            text = { Text(text = "BRL") },
            leadingIcon = {
                if (currentCurrency == "BRL") Icon(imageVector = Icons.Default.Done, contentDescription = null) else null
            },
            onClick = {
                checked = !checked
            }
        )
        DropdownMenuItem(
            text = { Text(text = "USD") },
            leadingIcon = {
                if (currentCurrency == "USD") Icon(imageVector = Icons.Default.Done, contentDescription = null) else null
            },
            onClick = {
                checked = !checked
            }
        )
        DropdownMenuItem(
            text = { Text(text = "EUR") },
            leadingIcon = {
                if (currentCurrency == "EUR") Icon(imageVector = Icons.Default.Done, contentDescription = null) else null
            },
            onClick = {
                checked = !checked
            }
        )
        DropdownMenuItem(
            text = { Text(text = "MZN") },
            leadingIcon = {
                if (currentCurrency == "MZN") Icon(imageVector = Icons.Default.Done, contentDescription = null) else null
            },
            onClick = {
                checked = !checked
            }
        )

    }
}

@Preview
@Composable
fun SplitPreview() {
    //CurrenciesSplitButton(currentCurrency = "USD")
}