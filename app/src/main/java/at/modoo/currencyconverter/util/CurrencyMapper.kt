package at.modoo.currencyconverter.util

import at.modoo.currencyconverter.localJson.Country
import at.modoo.currencyconverter.model.Currency
import at.modoo.currencyconverter.network.model.currencyResponse.CurrencyResponse

object CurrencyMapper {
    fun mapFromNetworkToModel(networkEntity: DataState.Success<CurrencyResponse>, jsonString: ArrayList<Country>): List<Currency> {
       val names = networkEntity.data.data.keys.toList()
        var list:ArrayList<Currency> = ArrayList()
        jsonString.map {
            if(networkEntity.data.data.keys.contains(it.currency.code)) {
                val rate = networkEntity.data.data[it.currency.code]
                list.add(Currency(code = it.currency.code,countryName = it.name,symbol = it.currency.symbol
                    ,currencyName = it.currency.name,it.flag,it.id,rate= networkEntity.data.data[it.currency.code]!!))
            }
        }

        return list

    }




}