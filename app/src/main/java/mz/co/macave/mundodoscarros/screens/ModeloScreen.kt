package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.models.Modelo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModeloList(modelosList: List<Modelo>, onModeloClickItem: (Modelo) -> Unit) {
    modelosList.let {

        val groupedItems = modelosList.groupBy { it.nome.first().uppercase() }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            groupedItems.forEach { (initial, group) ->
                stickyHeader {
                    ModeloStickyHeader(letter = initial)
                }
                items(items = group) {modelo ->
                    ModeloItem(modelo = modelo, onModeloClickItem = onModeloClickItem)
                }
            }
        }
    }
}

@Composable
fun ModeloStickyHeader(letter: String) {
    Box(
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.primary)
            .size(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text =letter,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Composable
fun ModeloItem(modelo: Modelo, onModeloClickItem: (Modelo)-> Unit) {
    ListItem(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onModeloClickItem(modelo) },
        headlineContent = {
            Text(
                text = modelo.nome,
                modifier = Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 2.dp
                )
            )
        }
    )
}
