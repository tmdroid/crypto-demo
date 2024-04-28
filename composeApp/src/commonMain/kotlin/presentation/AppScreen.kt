package presentation

sealed class AppScreen {
    data object Tickers : AppScreen()
    data class TickerDetails(val symbol: String) : AppScreen()
}