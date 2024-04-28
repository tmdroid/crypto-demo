package presentation.tickers

import domain.CryptoRepository
import domain.GetTickersUseCase
import domain.model.Ticker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TickersScreenViewModelTest {

    private lateinit var viewModel: TickersScreenViewModel


    @BeforeTest
    fun setUp() {
        viewModel = TickersScreenViewModel(
            getTickersUseCase = GetTickersUseCase(StubCryptoRepository()),
            domainTickerToTickerUiModelMapper = DomainTickerToTickerUiModelMapper()
        )
    }

    @Test
    fun `when search query is empty then all tickers are displayed`() = runTest {
        // Given
        viewModel.onSearchQueryChange("")

        // When
        val uiState = viewModel.tickers.skipLoading().first() as TickersScreenUiState.Success

        // Then
        val tickers = uiState.tickers
        assertEquals(TICKERS.size, tickers.size)
        assertEquals(TICKERS.map { it.symbol }, tickers.map { it.symbol })
    }

    @Test
    fun `when search query is not empty then only matching tickers are displayed`() = runTest {
        // Given
        viewModel.onSearchQueryChange("btc")

        // When
        val uiState = viewModel.tickers.skipLoading().first() as TickersScreenUiState.Success

        // Then
        val tickers = uiState.tickers
        assertEquals(1, tickers.size)
        assertEquals("BTCUSD", tickers.first().symbol)
    }

    private fun Flow<TickersScreenUiState>.skipLoading(): Flow<TickersScreenUiState> = filter {
        it !is TickersScreenUiState.Loading
    }

    private class StubCryptoRepository : CryptoRepository {
        override suspend fun getTickers(symbols: List<String>): List<Ticker> = TICKERS
    }

    companion object {
        private val TICKERS = listOf(
            Ticker("BTCUSD", "icon-btc", 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 2.0),
            Ticker("ETHUSD", "icon-eth", 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 1.0)
        )
    }

}