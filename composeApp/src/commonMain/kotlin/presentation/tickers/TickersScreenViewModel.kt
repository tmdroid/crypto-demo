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
    private val getTickersUseCase: GetTickersUseCase
) : CryptoViewModel() {

    val tickers = flow {
        while (currentCoroutineContext().isActive) {
            try {
                val tickers = getTickersUseCase.execute(TICKERS)
                emit(tickers)
            } catch (e: Exception) {
                e.printStackTrace()
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