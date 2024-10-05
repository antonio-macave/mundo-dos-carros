package mz.co.macave.mundodoscarros

import AppBar
import VeiculoModalBottomSheet
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.models.Modelo
import mz.co.macave.mundodoscarros.screens.AnosList
import mz.co.macave.mundodoscarros.screens.ErrorScreen
import mz.co.macave.mundodoscarros.screens.LoadingScreen
import mz.co.macave.mundodoscarros.ui.theme.MundoDosCarrosTheme
import mz.co.macave.mundodoscarros.utils.NetworkUtils
import mz.co.macave.mundodoscarros.viewmodel.AnoViewModel
import mz.co.macave.mundodoscarros.viewmodel.AnoViewModelFactory

class AnosActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val marca = getMarcaFromPreviousActivity()
            val modelo = getModeloFromPreviousActivity()

            MundoDosCarrosTheme {
                //BottomSheet vars
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                val snackbarHost =  remember { SnackbarHostState() }
                var isBottomSheetVisible by remember { mutableStateOf(false) }
                val bottomSheetState = rememberModalBottomSheetState()
                val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBar(
                            title = modelo.nome,
                            scrollBehavior = scrollBehavior,
                            navigationIcon = {
                                IconButton(
                                    onClick = { finish() }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        )
                    },
                    snackbarHost = { SnackBarHostage(host = snackbarHost) }

                ) { innerPadding ->

                    val viewModel: AnoViewModel = viewModel(factory = AnoViewModelFactory(marca = marca, modelo = modelo))
                    val isLoading by viewModel.isLoading.collectAsState()
                    val isNetworkError by viewModel.isNetworkError.collectAsState()
                    val anosList by viewModel.anos.collectAsState()
                    val veiculoSeleccionado by viewModel.veiculoSeleccionado.collectAsState()
                    val isBottomSheetLoading by viewModel.isBottomSheetLoading.collectAsState()

                    Column(
                        Modifier.padding(innerPadding)
                    ) {

                        if (isLoading) {
                            LoadingScreen()
                        } else {
                            if (isNetworkError) {
                                ErrorScreen {
                                    if (NetworkUtils.isInternetAvailable(context)) {
                                        viewModel.fetchAnos(
                                            marca = marca,
                                            modelo = modelo
                                        )
                                    } else {
                                        coroutineScope.launch {
                                            snackbarHost.showSnackbar(message = getString(R.string.network_error_message))
                                        }
                                    }
                                }
                            } else {
                                AnosList(anosList = anosList) {ano ->
                                    isBottomSheetVisible = true
                                    viewModel.fetchVeiculo(
                                        marca = marca,
                                        modelo = modelo,
                                        ano = ano
                                    )
                                }
                            }
                        }
                    }
                    if (isBottomSheetVisible) {
                        VeiculoModalBottomSheet(
                            sheetState = bottomSheetState,
                            isLoading = isBottomSheetLoading,
                            veiculo = veiculoSeleccionado
                        ) {
                            coroutineScope.launch {
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    isBottomSheetVisible = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SnackBarHostage(host: SnackbarHostState) {
        SnackbarHost(hostState = host)
    }

    private fun getMarcaFromPreviousActivity(): Marca {
        val code = intent.getStringExtra("marcaCodigo") ?: ""
        val nome = intent.getStringExtra("marcaNome") ?: ""
        return Marca(code, nome)
    }

    private fun getModeloFromPreviousActivity(): Modelo {
        val code = intent.getIntExtra("modeloCodigo", 0)
        val nome = intent.getStringExtra("modeloNome") ?: ""
        return Modelo(code, nome)
    }
}