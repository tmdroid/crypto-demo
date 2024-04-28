package data.remote

import domain.model.Ticker

interface CryptoDataSource {
    val type : CryptoDataSourcesConfig.DataSourceType
    val isActive: Boolean

    suspend fun getTickers(): List<Ticker>
}