package domain

import data.remote.CryptoRepositoryImpl
import data.remote.bitfinex.BitfinexService
import data.remote.bitfinex.dto.BitfinexTickerDataDto
import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import data.remote.mapper.BitfinanceTickerDtoToDomainTickerMapper
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetTickersUseCaseTest {

    private lateinit var useCase: GetTickersUseCase
    private lateinit var repository: CryptoRepository
    private lateinit var localBitfinexService: BitfinexService

    @BeforeTest
    fun setup() {
        val mapper = BitfinanceTickerDtoToDomainTickerMapper()
        localBitfinexService = StubBitfinexServiceImpl()
        repository = CryptoRepositoryImpl(localBitfinexService, mapper)
        useCase = GetTickersUseCase(repository)
    }

    @Test
    fun `when execute then getTickers from repository`() = runTest {
        // Given
        val symbols = listOf(ALL_TICKERS)

        // When
        val tickers = useCase.execute(symbols)

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
        private const val ALL_TICKERS = "ALL"
    }
}