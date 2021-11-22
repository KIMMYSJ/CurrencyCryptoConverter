package at.modoo.currencyconverter.network

import at.modoo.currencyconverter.network.model.currencyResponse.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface CurrencyAPI {
    @GET("latest")
    @Headers("Accept: application/json")
    suspend fun getRates(
        @Query("apikey")
        access_key:String,
        @Query("base_currency")
        base:String
    ): Response<CurrencyResponse>
}