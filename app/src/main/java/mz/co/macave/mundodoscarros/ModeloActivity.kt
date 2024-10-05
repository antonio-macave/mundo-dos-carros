package mz.co.macave.mundodoscarros

import AppBar
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.models.Modelo
import mz.co.macave.mundodoscarros.screens.ErrorScreen
import mz.co.macave.mundodoscarros.screens.LoadingScreen
import mz.co.macave.mundodoscarros.screens.ModeloItem
import mz.co.macave.mundodoscarros.screens.ModeloList
import mz.co.macave.mundodoscarros.ui.theme.MundoDosCarrosTheme
import mz.co.macave.mundodoscarros.utils.NetworkUtils
import mz.co.macave.mundodoscarros.viewmodel.ModeloViewModel
import mz.co.macave.mundodoscarros.viewmodel.ModeloViewModelFactory

class ModeloActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MundoDosCarrosTheme {

                val context = LocalContext.current
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val nome = intent.getStringExtra("nome") ?: ""
                val codigo = intent.getStringExtra("codigo") ?: ""
                val marca = Marca(codigo, nome)
                val scrollBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBarBehavior.nestedScrollConnection),
                    topBar = {
                        AppBar(
                            title = marca.nome,
                            scrollBehavior = scrollBarBehavior,
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    },
                    snackbarHost = {  SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Column(
                        Modifier.padding(innerPadding)
                    ) {
                        val viewModel: ModeloViewModel = viewModel(factory = ModeloViewModelFactory(marca))
                        val isLoading by viewModel.isLoading.collectAsState()
                        val isNetworkError by viewModel.isNetworkError.collectAsState()
                        val modelos by viewModel.models.collectAsState()

                        if (isLoading) {
                            LoadingScreen()
                        } else {

                            if (isNetworkError) {
                                ErrorScreen {
                                    if (NetworkUtils.isInternetAvailable(context)) {
                                        viewModel.fetchModelos(marca = marca)
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = getString(R.string.network_error_message))
                                        }
                                    }
                                }
                            } else {
                                ModelosSearchBar(marca = marca, items = modelos.modelos)
                                modelos.modelos?.let {
                                    ModeloList(modelosList = it) { currentModelo ->
                                        val i = Intent(context, AnosActivity::class.java).apply {
                                            putExtra("marcaCodigo", marca.codigo)
                                            putExtra("marcaNome", marca.nome)
                                            putExtra("modeloCodigo", currentModelo.codigo)
                                            putExtra("modeloNome", currentModelo.nome)
                                        }
                                        context.startActivity(i)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelosSearchBar(
    marca: Marca,
    items: List<Modelo>?
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var filteredItems by remember {
        mutableStateOf<List<Modelo>?>(emptyList())
    }

    LaunchedEffect(query) {
        filteredItems = if (query.isNotEmpty()) {
            items?.filter { item ->
                item.nome.contains(query, ignoreCase = true)
            }
        } else {
            emptyList()
        }
    }

    DockedSearchBar(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = { },
        active = active,
        onActiveChange = { active = !active },
        placeholder = { Text(text = stringResource(id = R.string.search_model)) },
        leadingIcon = {
            if (active) {
                IconButton(onClick = { active = !active }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            } else {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { query = "" }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    ) {
        LazyColumn {
            items(filteredItems!!) {currentModelo ->
                ModeloItem(modelo = currentModelo) {
                    val i = Intent(context, AnosActivity::class.java).apply {
                        putExtra("marcaCodigo", marca.codigo)
                        putExtra("marcaNome", marca.nome)
                        putExtra("modeloCodigo", currentModelo.codigo)
                        putExtra("modeloNome", currentModelo.nome)
                    }
                    context.startActivity(i)
                }
            }
        }
    }
}