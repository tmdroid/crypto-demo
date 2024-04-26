package presentation.tickers

import androidx.compose.ui.graphics.Color

sealed class TickersScreenUiState {
    object Loading : TickersScreenUiState()
    data class Success(val tickers: List<TickerUiModel>) : TickersScreenUiState()
    data class Error(val message: String) : TickersScreenUiState()
}

data class TickerUiModel(
    val iconUrl: String,
    val symbol: String,
    val rate: Double,
    val dailyChange: String,
    val dailyChangeColor: Color
)