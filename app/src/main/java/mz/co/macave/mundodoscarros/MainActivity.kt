package mz.co.macave.mundodoscarros

import AppBar
import MarcaItem
import MarcasList
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.screens.CurrenciesBottomSheet
import mz.co.macave.mundodoscarros.screens.ErrorScreen
import mz.co.macave.mundodoscarros.screens.LoadingScreen
import mz.co.macave.mundodoscarros.ui.theme.MundoDosCarrosTheme
import mz.co.macave.mundodoscarros.utils.ChosenCurrency
import mz.co.macave.mundodoscarros.utils.NetworkUtils
import mz.co.macave.mundodoscarros.viewmodel.MainActivityViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {

    val context = LocalContext.current

    val bottomSheetSate = rememberModalBottomSheetState()
    var isBottomSheetVisible by remember { mutableStateOf(false) }

    val scrollbarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val coroutineScope = rememberCoroutineScope()
    val snackBarHost = remember { SnackbarHostState() }

    MundoDosCarrosTheme {

        val viewModel: MainActivityViewModel = viewModel()
        val rates by viewModel.currencies.collectAsState()
        val marcas by viewModel.marcas.collectAsState()
        val loading by viewModel.isLoading.collectAsState()
        val networkError by viewModel.networkError.collectAsState()

        val currencies by viewModel.currencies.collectAsState()
        var isFabVisible by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = currencies) {
            viewModel.fetchCurrency { }
            if (currencies != null) {
                isFabVisible = currencies!!.rates.isNotEmpty()
            }
        }

        Scaffold(
            modifier = Modifier
                .statusBarsPadding()
                .nestedScroll(scrollbarBehavior.nestedScrollConnection),
            topBar = {

                AppBar(
                    scrollBehavior = scrollbarBehavior,
                    title = stringResource(id = R.string.app_name)
                )

            },
            floatingActionButton = {
                if (isFabVisible) {
                    FabCurrency {
                        isBottomSheetVisible = true
                    }
                }
            },
            snackbarHost = {
                SnackBarShow(hostState = snackBarHost)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {

                if (!networkError) {
                    if (loading) {
                        LoadingScreen()
                    } else {
                        TopSearchBar(marcas)
                        MarcasList(marcas)
                    }
                } else {
                    ErrorScreen {
                        if (NetworkUtils.isInternetAvailable(context)) {
                            viewModel.fetchMarcas()
                        } else {
                            coroutineScope.launch {
                                snackBarHost.showSnackbar(
                                    message = context.getString(R.string.network_error_message)
                                )
                            }
                        }
                    }
                }

                if (isBottomSheetVisible) {
                    val currentCurrency = ChosenCurrency.currency
                    CurrenciesBottomSheet(
                        currentCurrency = currentCurrency,
                        rates = rates!!.rates,
                        state = bottomSheetSate,

                        onCurrencySelected = {
                            rates!!.rates[it]?.let { it1 ->
                                ChosenCurrency.changeCurrency(it, it1)
                            }
                            coroutineScope.launch {
                                bottomSheetSate.hide()
                            }.invokeOnCompletion {
                                if (!bottomSheetSate.isVisible) {
                                    isBottomSheetVisible = false
                                }
                            }
                        },

                        onDismissListener = {
                            coroutineScope.launch {
                                bottomSheetSate.hide()
                            }.invokeOnCompletion {
                                if (!bottomSheetSate.isVisible) {
                                    isBottomSheetVisible = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(
    items: List<Marca>,
) {
    val context = LocalContext.current
    var active by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    var filteredItems by remember {
        mutableStateOf<List<Marca>>(emptyList())
    }

    LaunchedEffect(query) {
        filteredItems = if (query.isNotEmpty()) {
            items.filter {
                it.nome.contains(query, ignoreCase = true)
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
        onSearch = {   },
        active = active,
        onQueryChange = {
            query = it
        },
        onActiveChange = {
            active = it
        },
        placeholder = { Text(text = stringResource(id = R.string.search_marca)) },
        leadingIcon = {
            if (active) {
                IconButton(onClick = { active = !active }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
        },

        ) {

        LazyColumn {
            items(filteredItems) { item ->
                MarcaItem(marca = item) {
                    val intent = Intent(context, ModeloActivity::class.java).apply {
                        putExtra("codigo", item.codigo)
                        putExtra("nome", item.nome)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}


@Composable
fun FabCurrency(
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        text = { Text(text = stringResource(id = R.string.currency)) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.currency_24),
                contentDescription = stringResource(R.string.currency)
            )
        },
        onClick = onClick
    )
}

@Composable
fun SnackBarShow(hostState: SnackbarHostState) {
    SnackbarHost(hostState = hostState)
}