package data.remote

import domain.model.Ticker

interface CryptoDataSource {
    val type : DataSourcesConfig.DataSourceType
    val isActive: Boolean

    suspend fun getTickers(): List<Ticker>
}