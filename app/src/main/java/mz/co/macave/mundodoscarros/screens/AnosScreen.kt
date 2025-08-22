package mz.co.macave.mundodoscarros.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.models.Ano
import mz.co.macave.mundodoscarros.ui.theme.OnScreenBackGroundContainer
import mz.co.macave.mundodoscarros.ui.theme.ScreenBackground


@Composable
fun AnosList(anosList: List<Ano>, onAnoClickItem: (Ano) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = ScreenBackground),
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        itemsIndexed(items = anosList) { index, _item ->
            ShapedAnoItems(
                items = anosList,
                index = index,
                onItemClick = onAnoClickItem
            )
        }
    }
}

@Composable
fun ShapedAnoItems(items: List<Ano>, index: Int, onItemClick: (Ano) -> Unit) {
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
            .background(color = OnScreenBackGroundContainer)
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