package mz.co.macave.mundodoscarros.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mz.co.macave.mundodoscarros.R
import mz.co.macave.mundodoscarros.models.CurrencyRate
import mz.co.macave.mundodoscarros.models.Marca
import mz.co.macave.mundodoscarros.utils.SharedPrefsManager
import mz.co.macave.mundodoscarros.utils.client


class MainActivityViewModel: ViewModel() {

    private var sharedPrefs: SharedPreferences? = null

    private val _marcas = MutableStateFlow<List<Marca>>(emptyList())
    val marcas: StateFlow<List<Marca>> get() = _marcas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _currencies = MutableStateFlow<CurrencyRate?>(null)
    val currencies: StateFlow<CurrencyRate?> get() = _currencies.asStateFlow()

    private val _isCurrencyLoading = MutableStateFlow(false)

    private val _networkError = MutableStateFlow(false)
    val networkError: StateFlow<Boolean> get() = _networkError.asStateFlow()

    init {
        fetchMarcas()
    }


    fun fetchCurrency(whenFinished: () -> Unit) {
        _isCurrencyLoading.value = true
        viewModelScope.launch {
            try {
                val response = client.get("https://api.exchangerate-api.com/v4/latest/brl")
                if (response.status.isSuccess()) {
                    _currencies.value = response.body()
                }
            } catch (_: Exception) {

            } finally {
                _isCurrencyLoading.value = false
            }
            whenFinished()
        }
    }

    fun fetchMarcas() {
        _networkError.value = false
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = client.get("https://parallelum.com.br/fipe/api/v1/carros/marcas")
                if (response.status.isSuccess()) {
                    _marcas.value = response.body()
                }
            } catch (e: Exception) {
                _networkError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun savePrefs(context: Context) {
        sharedPrefs = context.getSharedPreferences(context.getString(R.string.prefs_key_currency), Context.MODE_PRIVATE)
        val prefsManager = SharedPrefsManager(context, sharedPrefs as SharedPreferences)
        prefsManager.saveKey(context.getString(R.string.prefs_key_currency))
    }

    override fun onCleared() {
        super.onCleared()
        client.close()
    }
}