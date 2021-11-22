package at.modoo.currencyconverter.localJson


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("currency")
    val currency: Currency,
    @SerializedName("flag")
    val flag: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isoAlpha2")
    val isoAlpha2: String,
    @SerializedName("isoAlpha3")
    val isoAlpha3: String,
    @SerializedName("isoNumeric")
    val isoNumeric: Int,
    @SerializedName("name")
    val name: String
)