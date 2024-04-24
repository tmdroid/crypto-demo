package data.remote

import data.remote.bitfinex.BitfinexService
import data.remote.mapper.BitfinanceTickerDtoToDomainTickerMapper
import domain.CryptoRepository
import domain.model.Ticker

class CryptoRepositoryImpl(
    private val bitfinexService: BitfinexService,
    private val mapper: BitfinanceTickerDtoToDomainTickerMapper,
) : CryptoRepository {

    override suspend fun getTickers(symbols: List<String>): List<Ticker> =
        mapper.map(bitfinexService.getTickers(symbols))
}