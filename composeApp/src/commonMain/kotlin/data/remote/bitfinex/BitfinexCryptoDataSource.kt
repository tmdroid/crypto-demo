package data.remote.bitfinex

import data.remote.CryptoDataSource
import data.remote.CryptoDataSourcesConfig.DataSourceType
import data.remote.GetDataSourcesConfig
import domain.model.Ticker

class BitfinexCryptoDataSource(
    private val bitfinexService: BitfinexService,
    private val mapper: BitfinanceTickerDtoToDomainTickerMapper,
    private val config: GetDataSourcesConfig,
) : CryptoDataSource {

    override val type: DataSourceType get() = DataSourceType.BITFINEX

    override val isActive: Boolean get() = config.getIsActive(type)

    override suspend fun getTickers(): List<Ticker> =
        mapper.map(bitfinexService.getTickers(TICKERS))

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