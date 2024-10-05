package mz.co.macave.mundodoscarros.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.models.Ano
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.models.Modelo
import mz.co.macave.mundodoscarros.models.Veiculo
import mz.co.macave.mundodoscarros.utils.client

class AnoViewModel(marca: Marca, modelo: Modelo) : ViewModel() {

    private val _anos: MutableStateFlow<List<Ano>> = MutableStateFlow(emptyList())
    val anos: StateFlow<List<Ano>> get() = _anos.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _isNetworkError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNetworkError: StateFlow<Boolean> get() = _isNetworkError.asStateFlow()

    private val _isBottomSheetLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBottomSheetLoading: StateFlow<Boolean> get() = _isBottomSheetLoading.asStateFlow()

    private val _veiculoSeleccionado: MutableStateFlow<Veiculo> = MutableStateFlow(Veiculo())
    val veiculoSeleccionado: StateFlow<Veiculo> get() = _veiculoSeleccionado.asStateFlow()

    init {
        fetchAnos(marca, modelo)
    }

    fun fetchAnos(marca: Marca, modelo: Modelo) {
        _isNetworkError.value = false
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val  response = client.get("https://parallelum.com.br/fipe/api/v1/carros/marcas/${marca.codigo}/modelos/${modelo.codigo}/anos")
                if (response.status.isSuccess()) {
                    _anos.value = response.body()
                }
            } catch (_: Exception) {
                _isNetworkError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchVeiculo(marca: Marca, modelo: Modelo, ano: Ano) {
        _isNetworkError.value = false
        _isBottomSheetLoading.value = true
        viewModelScope.launch {
            try {
                val response = client.get("https://parallelum.com.br/fipe/api/v1/carros/marcas/${marca.codigo}/modelos/${modelo.codigo}/anos/${ano.codigo}")
                if (response.status.isSuccess()) {
                    _veiculoSeleccionado.value = response.body()
                }
            } catch (_: Exception) {
                _isNetworkError.value = true
            } finally {
                _isBottomSheetLoading.value = false
            }
        }
    }
}

class AnoViewModelFactory(private val marca: Marca, private val modelo: Modelo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if(modelClass.isAssignableFrom(AnoViewModel::class.java)) {
            return AnoViewModel(marca = marca, modelo = modelo) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
