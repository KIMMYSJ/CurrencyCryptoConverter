package at.modoo.currencyconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.modoo.currencyconverter.model.Crypto
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.network.model.cryptoResponse.CryptoResponse
import at.modoo.currencyconverter.network.model.currencyResponse.CurrencyResponse
import at.modoo.currencyconverter.repository.CurrencyRepository
import at.modoo.currencyconverter.util.ConverterState
import at.modoo.currencyconverter.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    private val _currency = MutableStateFlow<DataState<List<Currency>>>(DataState.Empty)
    val currency: StateFlow<DataState<List<Currency>>>
        get() = _currency
    lateinit var converterType:ConverterState
    var targetTo:Long = 0L
    var targetFrom:Long = 0L


    private val _crypto = MutableStateFlow<DataState<List<Crypto>>>(DataState.Empty)
    val crypto: StateFlow<DataState<List<Crypto>>>
        get() = _crypto

    fun getCrypto() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCrypto().collect { dataState ->
                _crypto.value = dataState
                }
        }
    }

    fun getRate(json: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {

            val jsonString:String = convertToJsonString(json)
            repository.getCurrency(jsonString).collect { dataState ->
                _currency.value = dataState
            }
        }
    }

    private fun convertToJsonString(json: InputStream):String {

        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader: Reader = BufferedReader(InputStreamReader(json, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            json.close()
        }
        return writer.toString()

    }

    fun convert(amount: String, from: String, to: String){

    }

    fun calculate(amount: Double, from: Double, to: Double): Double{
        return (amount * to)/ from
    }
}

