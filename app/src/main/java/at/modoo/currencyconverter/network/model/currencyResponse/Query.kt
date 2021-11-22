package at.modoo.currencyconverter.network.model.currencyResponse


import com.google.gson.annotations.SerializedName

data class Query(
    @SerializedName("base_currency")
    val baseCurrency: String,
    @SerializedName("timestamp")
    val timestamp: Int
)