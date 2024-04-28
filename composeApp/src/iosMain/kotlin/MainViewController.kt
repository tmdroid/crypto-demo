import androidx.compose.ui.window.ComposeUIViewController
import di.initializeKoin
import presentation.App
import presentation.tickerdetails.TickerDetailsScreenView
import presentation.tickers.TickersScreenView
import util.getScreenView

fun MainViewController() = ComposeUIViewController {
    val koin = initializeKoin()
    val tickersScreenView = koin.getScreenView<TickersScreenView>()
    val tickerDetailsScreenView = koin.getScreenView<TickerDetailsScreenView>()
    App(tickersScreenView, tickerDetailsScreenView)
}