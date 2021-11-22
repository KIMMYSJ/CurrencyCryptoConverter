package at.modoo.currencyconverter.util

interface EntityMapper<NetworkEntity,Model> {
    fun mapFromNetworkToModel(networkEntity:NetworkEntity):Model
    fun mapFromModelToNetwork(model:Model):NetworkEntity
}