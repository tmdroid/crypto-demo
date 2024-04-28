package data.remote.bitfinex

import data.remote.CryptoDataSource
import data.remote.DataSourcesConfig
import data.remote.DataSourcesConfig.DataSourceType
import domain.model.Ticker

class BitfinexDataSource(
    private val bitfinexService: BitfinexService,
    private val mapper: BitfinanceTickerDtoToDomainTickerMapper,
    private val config: DataSourcesConfig,
) : CryptoDataSource {

    override val type: DataSourceType get() = DataSourceType.BITFINEX

    override val isActive: Boolean get() = config.getIsActive(type)

    override suspend fun getTickers(): List<Ticker> = mapper.map(bitfinexService.getTickers(TICKERS))

    companion object {
        private val TICKERS = listOf(
            "tBTCUSD",
            "tETHUSD",
            "tTRXUSD",
            "tLTCUSD",
            "tXRPUSD",
            "tSOLUSD",
            "tTONUSD",
            "tEOSUSD",
            "tBCHUSD",
            "tICPUSD",
            "tSHIB:USD",
            "tDOGE:USD",
            "tMATIC:USD",
            "tNEXO:USD",
            "tOCEAN:USD",
            "tAAVE:USD",
            "tLINK:USD",
            "tFILUSD"
        )
    }
}