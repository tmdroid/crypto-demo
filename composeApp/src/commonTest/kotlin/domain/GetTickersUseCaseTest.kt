package domain

import data.remote.CryptoRepositoryImpl
import data.remote.bitfinex.LocalBitfinexServiceImpl
import data.remote.mapper.BitfinanceTickerDtoToDomainTickerMapper
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetTickersUseCaseTest {

    private lateinit var useCase: GetTickersUseCase
    private lateinit var repository: CryptoRepository

    @BeforeTest
    fun setup() {
        val mapper = BitfinanceTickerDtoToDomainTickerMapper()
        repository = CryptoRepositoryImpl(LocalBitfinexServiceImpl(), mapper)
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
        assertTrue { tickers.size == 1 }
    }

    companion object {
        private const val ALL_TICKERS = "ALL"
    }

}