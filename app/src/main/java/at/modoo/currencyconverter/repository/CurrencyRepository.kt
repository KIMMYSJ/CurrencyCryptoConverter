package at.modoo.currencyconverter.repository

import at.modoo.currencyconverter.model.Crypto
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.network.model.cryptoResponse.CryptoResponse
import at.modoo.currencyconverter.network.model.currencyResponse.CurrencyResponse
import at.modoo.currencyconverter.util.DataState
import kotlinx.coroutines.flow.Flow


interface CurrencyRepository{
    suspend fun getCurrency(jsonString: String): Flow<DataState<List<Currency>>>
    suspend fun getCrypto():Flow<DataState<List<Crypto>>>
}