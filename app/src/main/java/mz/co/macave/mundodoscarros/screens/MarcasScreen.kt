import android.content.Intent
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import mz.co.macave.mundodoscarros.ModeloActivity
import mz.co.macave.mundodoscarros.models.Marca


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarcasList(
    list: List<Marca>,
) {
    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val groupedItems = list.groupBy { it.nome.first().uppercase() }

        groupedItems.forEach { (initial, group) ->

            stickyHeader {
                StickyHeader(letter = initial)
            }
            items(items = group) { marca ->
                MarcaItem(marca = marca) {
                    val intent = Intent(context, ModeloActivity::class.java).apply {
                        putExtra("codigo", marca.codigo)
                        putExtra("nome", marca.nome)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}


@Composable
fun StickyHeader(letter: String) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Composable
fun MarcaItem(marca: Marca, onItemClick: (Marca)-> Unit) {
    ListItem(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onItemClick(marca) },
        headlineContent = {
            Text(
                text = marca.nome,
                modifier = Modifier.padding(
                    horizontal = 18.dp,
                    vertical = 2.dp
                )
            )
        },
        shadowElevation = 2.dp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior?,
    navigationIcon: @Composable () -> Unit = {   },
) {
    MediumTopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = navigationIcon,
    )
}