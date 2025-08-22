package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import mz.co.macave.mundodoscarros.ui.theme.ScreenBackground


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ModeloList(modelosList: List<Modelo>, onModeloClickItem: (Modelo) -> Unit) {
    modelosList.let {

        val groupedItems = modelosList.groupBy { it.nome.first().uppercase() }
        LazyColumn(
            modifier = Modifier
                .background(color = ScreenBackground)
                .fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            groupedItems.forEach { (initial, group) ->
                stickyHeader {
                    ModeloStickyHeader(letter = initial)
                }
                itemsIndexed(items = group) { index, _ ->
                    ShapedModeloItems(items = group, index = index, onItemClick = onModeloClickItem)
                }
            }
        }
    }
}

@Composable
fun ShapedModeloItems(items: List<Modelo>, index: Int, onItemClick: (Modelo) -> Unit) {
    ListItem(
        modifier = Modifier
            .clip(
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )
                    items.lastIndex -> RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    )
                    else -> RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomStart = 4.dp,
                        bottomEnd = 4.dp
                    )
                }
            )
            //.background(color = OnScreenBackGroundContainer)
            .clickable {
                onItemClick(items[index])
            },
        headlineContent = {
            Text(
                text = items[index].nome,
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 2.dp
                )
            )
        },
    )
}

@Composable
fun ModeloStickyHeader(letter: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
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
