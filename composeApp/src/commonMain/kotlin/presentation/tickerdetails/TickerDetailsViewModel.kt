package presentation.tickerdetails

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import presentation.CryptoViewModel

sealed class TickerDetailsUiState {
    data object Loading : TickerDetailsUiState()
    data class Success(val ticker: String) : TickerDetailsUiState()
    data class Error(val message: String) : TickerDetailsUiState()
}

class TickerDetailsViewModel : CryptoViewModel() {
    fun getUiState(symbol: String): Flow<TickerDetailsUiState> {
        return flow {
            emit(TickerDetailsUiState.Loading)
            delay(1000L)
            emit(TickerDetailsUiState.Success(ticker = symbol))
        }
    }
}
