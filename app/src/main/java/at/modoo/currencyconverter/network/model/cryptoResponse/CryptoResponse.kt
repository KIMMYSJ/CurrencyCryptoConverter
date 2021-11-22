package at.modoo.currencyconverter.network.model.cryptoResponse


import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("status")
    val status: Status
)