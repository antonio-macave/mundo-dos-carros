package mz.co.macave.mundodoscarros.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.models.ModeloAno
import mz.co.macave.mundodoscarros.utils.client

class ModeloViewModel(marca: Marca) : ViewModel() {

    private val _models = MutableStateFlow(ModeloAno(null, null))
    val models: StateFlow<ModeloAno> get() = _models.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _isNetworkError = MutableStateFlow(false)
    val isNetworkError: StateFlow<Boolean> get() = _isNetworkError.asStateFlow()


    init {
        fetchModelos(marca)
    }

    fun fetchModelos(marca: Marca) {
        _isNetworkError.value = false
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = client.get("https://parallelum.com.br/fipe/api/v1/carros/marcas/${marca.codigo}/modelos")
                if (response.status.isSuccess()) {
                    _models.value = response.body()
                }
            } catch(_: Exception) {
                _isNetworkError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class ModeloViewModelFactory(private val marca: Marca) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ModeloViewModel::class.java)) {
            return ModeloViewModel(marca) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}