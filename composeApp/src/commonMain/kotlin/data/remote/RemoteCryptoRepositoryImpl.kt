package data.remote

import domain.CryptoRepository
import domain.model.Ticker

class RemoteCryptoRepositoryImpl(
    private val dataSources: List<CryptoDataSource>,
) : CryptoRepository {

    override suspend fun getTickers(): List<Ticker> =
        dataSources.filter { it.isActive }
            .map { dataSource -> dataSource.getTickers() }
            .flatten()
}