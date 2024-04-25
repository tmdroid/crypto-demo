package presentation.tickers

import domain.GetTickersUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import presentation.CryptoViewModel

class TickersScreenViewModel(
    private val getTickersUseCase: GetTickersUseCase,
    private val domainTickerToTickerUiModelMapper: DomainTickerToTickerUiModelMapper,
) : CryptoViewModel() {

    val tickers = flow {
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

    companion object {
        private const val REFRESH_TIME = 5000L
        private val TICKERS = listOf(
            "tBTCUSD",
            "tETHUSD",
            "tCHSB:USD",
            "tLTCUSD",
            "tXRPUSD",
            "tDSHUSD",
            "tRRTUSD",
            "tEOSUSD",
            "tSANUSD",
            "tDATUSD",
            "tSNTUSD",
            "tDOGE:USD",
            "tLUNA:USD",
            "tMATIC:USD",
            "tNEXO:USD",
            "tOCEAN:USD",
            "tBEST:USD",
            "tAAVE:USD",
            "tPLUUSD",
            "tFILUSD"
        )
    }

}