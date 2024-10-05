package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.models.Ano


@Composable
fun AnosList(anosList: List<Ano>, onAnoClickItem: (Ano) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp)
    ) {
        items(items = anosList) {item ->
            AnoItem(ano = item, onAnoClickItem = onAnoClickItem)
        }
    }
}

@Composable
fun AnoItem(ano: Ano, onAnoClickItem: (Ano) -> Unit) {
    ListItem(
        headlineContent = {
            Text(text = ano.nome)
        },
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onAnoClickItem(ano) }
    )
}