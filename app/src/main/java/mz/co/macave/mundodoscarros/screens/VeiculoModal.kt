package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.R
import mz.co.macave.mundodoscarros.models.Veiculo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoModalBottomSheet(
    sheetState: SheetState,
    isLoading: Boolean,
    veiculo: Veiculo,
    onDismissListener: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissListener,
        sheetState = sheetState,
    ) {
        Box {
            if(isLoading) {
                LoadingBottomSheet()
            } else {
                VeiculoDetails(veiculo = veiculo)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingBottomSheet() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = stringResource(R.string.loading))
    }
}