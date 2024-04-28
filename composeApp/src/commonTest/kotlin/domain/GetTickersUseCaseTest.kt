package domain

import data.remote.CryptoDataSourcesConfig
import data.remote.CryptoDataSourcesConfig.DataSourceType
import data.remote.RemoteCryptoRepositoryImpl
import data.remote.bitfinex.BitfinanceTickerDtoToDomainTickerMapper
import data.remote.bitfinex.BitfinexCryptoDataSource
import data.remote.bitfinex.BitfinexService
import data.remote.bitfinex.dto.BitfinexTickerDataDto
import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTickersUseCaseTest {

    private lateinit var useCase: GetTickersUseCase

    @BeforeTest
    fun setup() {
        val mapper = BitfinanceTickerDtoToDomainTickerMapper()
        val stubbedBitfinexService = StubBitfinexServiceImpl()
        val config = CryptoDataSourcesConfig(
            mapOf(DataSourceType.BITFINEX to true)
        )
        val dataSource = BitfinexCryptoDataSource(stubbedBitfinexService, mapper, config)
        val repository = RemoteCryptoRepositoryImpl(listOf(dataSource))
        useCase = GetTickersUseCase(repository)
    }

    @Test
    fun `when execute then getTickers from repository`() = runTest {
        // When
        val tickers = useCase.execute()

        // Then
        // Verify getTickers from repository
        assertEquals(TICKERS.size, tickers.size)
    }

    private class StubBitfinexServiceImpl : BitfinexService {
        override suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto =
            BitfinexTickersResponseDto(TICKERS)
    }

    companion object {
        private val TICKERS = listOf(
            BitfinexTickerDataDto(
                "tBTCUSD",
                65907.0,
                6.22136307,
                65908.0,
                5.49994209,
                -718.0,
                -0.01077366,
                65926.0,
                512.36852112,
                67233.0,
                65783.0
            )
        )
    }
}