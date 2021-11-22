package at.modoo.currencyconverter.network.model.cryptoResponse


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("cmc_rank")
    val cmcRank: Double,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("id")
    val id: Double,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("quote")
    var quote: Map<String,Quote>,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("total_supply")
    val totalSupply: Double
)