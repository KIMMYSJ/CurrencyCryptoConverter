package at.modoo.currencyconverter.util

import at.modoo.currencyconverter.model.Crypto
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.network.model.cryptoResponse.CryptoResponse
import timber.log.Timber

object CrytoMapper {
   fun mapFromNetworkToModel(networkEntity: CryptoResponse): List<Crypto> {
       val cryptoNameList = networkEntity.data.map { data -> data.symbol }
       var list:ArrayList<Crypto> = ArrayList()
            networkEntity.data.map {data->
                data.quote.map {
                   val s:Double = it.value.price
                    list.add(Crypto(data.name,data.symbol,it.value.price))
                    Timber.d("mapFromNetworkToModel: Crypto-> price $s, slug-> ${data.slug}")
                }
            }
       return list
    }

}