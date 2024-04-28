package data.remote

interface GetDataSourcesConfig {
    fun getIsActive(source: CryptoDataSourcesConfig.DataSourceType): Boolean
}

interface SetDataSourcesConfig {
    fun setIsActive(source: CryptoDataSourcesConfig.DataSourceType, isActive: Boolean)

    fun setActiveSources(activeSources: Map<CryptoDataSourcesConfig.DataSourceType, Boolean>)
}

class CryptoDataSourcesConfig(
    initialActiveSources: Map<DataSourceType, Boolean>
) : GetDataSourcesConfig, SetDataSourcesConfig {

    private val activeDataSources: MutableMap<DataSourceType, Boolean> =
        initialActiveSources.toMutableMap()

    enum class DataSourceType {
        BITFINEX,
        BINANCE,
        COINBASE
    }

    override fun getIsActive(source: DataSourceType): Boolean = activeDataSources[source] ?: false

    override fun setIsActive(source: DataSourceType, isActive: Boolean) {
        activeDataSources[source] = isActive
    }

    override fun setActiveSources(activeSources: Map<DataSourceType, Boolean>) {
        activeDataSources.clear()
        activeDataSources.putAll(activeSources)
    }
}