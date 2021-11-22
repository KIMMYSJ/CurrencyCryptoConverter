package at.modoo.currencyconverter.repository

import at.modoo.currencyconverter.BuildConfig
import at.modoo.currencyconverter.network.CryptoAPI
import at.modoo.currencyconverter.network.CurrencyAPI
import at.modoo.currencyconverter.localJson.Country
import at.modoo.currencyconverter.model.Crypto
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.util.CrytoMapper
import at.modoo.currencyconverter.util.CurrencyMapper
import at.modoo.currencyconverter.util.DataState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject


class DefaultCurrencyRepository
@Inject constructor(private val api_currency: CurrencyAPI, private val api_crypto: CryptoAPI):CurrencyRepository
{
    override suspend fun getCurrency(jsonString: String): Flow<DataState<List<Currency>>> = flow {
        try {
            val response = api_currency.getRates(BuildConfig.CURRENCY_PI_KEY, "USD")
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Timber.d("getCurrency: Success")
                val gson = Gson()
                val userListType: Type = object : TypeToken<ArrayList<Country?>?>() {}.type
                val userArray: ArrayList<Country> = gson.fromJson(jsonString, userListType)
                val list:List<Currency> = CurrencyMapper.mapFromNetworkToModel(DataState.Success(result),userArray)
                Timber.d("getCurrency: $userArray")
                emit(DataState.Success(list))
            } else {
                emit(DataState.ErrorUnknown(response.errorBody()?.toString() ?: "unknown"))
            }
        } catch (e: Exception) {
            Timber.d("getCurrency: Error " + e.message)
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCrypto(): Flow<DataState<List<Crypto>>> = flow {
        emit(DataState.Loading)
        try {
            val response = api_crypto.getCrypto(BuildConfig.CRYPTO_API_KEY)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                val result =  CrytoMapper.mapFromNetworkToModel(networkEntity = body)
                emit(DataState.Success(result))
            } else {

                emit(DataState.ErrorUnknown(response.body()?.status?.let {
                    it.errorMessage
                } ?: "Unknown Error"))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }.flowOn(Dispatchers.IO)




}