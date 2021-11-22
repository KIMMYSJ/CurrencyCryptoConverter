package at.modoo.currencyconverter.network.model.currencyResponse


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("data")
    @Expose
    val data: Map<String,Double>,
    @SerializedName("query")
    @Expose
    val query: Query
)