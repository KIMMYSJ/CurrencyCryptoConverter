package at.modoo.currencyconverter.di

import at.modoo.currencyconverter.common.Common
import at.modoo.currencyconverter.network.CryptoAPI
import at.modoo.currencyconverter.network.CurrencyAPI
import at.modoo.currencyconverter.repository.CurrencyRepository
import at.modoo.currencyconverter.repository.DefaultCurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): OkHttpClient {
        val builder =  OkHttpClient.Builder()
        val loggingInterceptor =  HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.networkInterceptors().add(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCurrencyAPI(client:OkHttpClient):CurrencyAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(
        Common.BASE_URL_CURRENCY).client(client).build().create(CurrencyAPI::class.java)

    @Singleton
    @Provides
    fun provideCryptoAPI(client:OkHttpClient):CryptoAPI = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(
            Common.BASE_URL_CRYPTO).client(client).build().create(CryptoAPI::class.java)

    @Singleton
    @Provides
    fun provideRepository(api:CurrencyAPI,api_2:CryptoAPI):CurrencyRepository
    = DefaultCurrencyRepository(api,api_2)


}