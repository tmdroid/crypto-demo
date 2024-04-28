package data.remote

class DataSourcesConfig(private val activeDataSources: Map<DataSourceType, Boolean>) {

    enum class DataSourceType {
        BITFINEX,
        BINANCE,
        COINBASE
    }

    fun getIsActive(source: DataSourceType): Boolean = activeDataSources[source] ?: false
}