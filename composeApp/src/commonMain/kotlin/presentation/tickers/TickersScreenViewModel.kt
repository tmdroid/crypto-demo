package presentation.tickers

import domain.GetTickersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import presentation.CryptoViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class TickersScreenViewModel(
    private val getTickersUseCase: GetTickersUseCase,
    private val domainTickerToTickerUiModelMapper: DomainTickerToTickerUiModelMapper,
) : CryptoViewModel() {

    var searchQuery = MutableStateFlow("")
        private set

    private val _tickers = flow {
        emit(TickersScreenUiState.Loading)

        while (currentCoroutineContext().isActive) {
            try {
                val tickers = getTickersUseCase.execute(TICKERS)
                    .let { domainTickerToTickerUiModelMapper.map(it) }
                    .let { TickersScreenUiState.Success(it) }
                emit(tickers)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(TickersScreenUiState.Error(e.message ?: "Unknown error"))
            }

            delay(REFRESH_TIME)
        }
    }.shareIn(viewModelScope, SharingStarted.Lazily, replay = 1)

    val tickers: Flow<TickersScreenUiState>
        get() = searchQuery.flatMapLatest { query ->
            if (query.isNotEmpty()) {
                _tickers.map { uiState ->
                    if (uiState is TickersScreenUiState.Success) {
                        val matches = uiState.tickers.filter { ticker ->
                            ticker.symbol.contains(query, ignoreCase = true)
                        }
                        uiState.copy(tickers = matches)
                    } else uiState
                }
            } else _tickers
        }

    fun onSearchQueryChange(query: String?) {
        searchQuery.update { query.orEmpty() }
    }

    companion object {
        private const val REFRESH_TIME = 5000L
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