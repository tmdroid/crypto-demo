package data.remote

import data.remote.CryptoDataSourcesConfig.DataSourceType
import domain.CryptoRepository
import domain.model.Ticker
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCryptoRepositoryImplTest {

    private lateinit var cryptoRepository: CryptoRepository
    private lateinit var cryptoDataSourcesConfig: CryptoDataSourcesConfig

    @BeforeTest
    fun setup() {
        cryptoDataSourcesConfig = CryptoDataSourcesConfig(
            mapOf(
                DataSourceType.BITFINEX to true,
                DataSourceType.BINANCE to true,
            )
        )
        cryptoRepository = RemoteCryptoRepositoryImpl(
            listOf(
                StubBitfinexCryptoDataSource(cryptoDataSourcesConfig),
                StubBinanceCryptoDataSource(cryptoDataSourcesConfig),
            )
        )
    }

    @Test
    fun `when getTickers then return the Tickers from active data sources`() = runTest {
        // When
        val tickers = cryptoRepository.getTickers()

        // Then
        assertEquals(BITFINEX_TICKERS + BINANCE_TICKERS, tickers)
    }

    @Test
    fun `when one data source is active then getTickers from it`() = runTest {
        // Given
        cryptoDataSourcesConfig.setActiveSources(
            mapOf(
                DataSourceType.BITFINEX to false,
                DataSourceType.BINANCE to true,
            )
        )

        // When
        val tickers = cryptoRepository.getTickers()

        // Then
        assertEquals(BINANCE_TICKERS, tickers)
    }

    private class StubBitfinexCryptoDataSource(
        private val cryptoDataSourcesConfig: CryptoDataSourcesConfig
    ) : CryptoDataSource {
        override val type: DataSourceType get() = DataSourceType.BITFINEX
        override val isActive: Boolean get() = cryptoDataSourcesConfig.getIsActive(type)
        override suspend fun getTickers(): List<Ticker> = BITFINEX_TICKERS
    }

    private class StubBinanceCryptoDataSource(
        private val cryptoDataSourcesConfig: CryptoDataSourcesConfig
    ) : CryptoDataSource {
        override val type: DataSourceType get() = DataSourceType.BINANCE
        override val isActive: Boolean get() = cryptoDataSourcesConfig.getIsActive(type)
        override suspend fun getTickers(): List<Ticker> = BINANCE_TICKERS
    }

    companion object {
        private val BITFINEX_TICKERS: List<Ticker> = listOf(
            Ticker(
                symbol = "tBTCUSD",
                icon = "icon",
                bid = 1.0,
                ask = 1.0,
                dailyChangePerc = 1.0,
                lastPrice = 1.0,
                volume = 1.0,
                high = 1.0,
                low = 1.0,
            )
        )
        private val BINANCE_TICKERS: List<Ticker> = listOf(
            Ticker(
                symbol = "ETHUSDT",
                icon = "icon",
                bid = 1.0,
                ask = 1.0,
                dailyChangePerc = 1.0,
                lastPrice = 1.0,
                volume = 1.0,
                high = 1.0,
                low = 1.0,
            )
        )
    }

}