package at.modoo.currencyconverter.network

import at.modoo.currencyconverter.network.model.cryptoResponse.CryptoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CryptoAPI {
    @GET("cryptocurrency/listings/latest")
    @Headers("Accept: application/json")
    suspend fun getCrypto(@Header("X-CMC_PRO_API_KEY")api_key:String):Response<CryptoResponse>
}