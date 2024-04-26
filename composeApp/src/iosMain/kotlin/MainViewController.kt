import androidx.compose.ui.window.ComposeUIViewController
import di.initializeKoin
import presentation.App
import presentation.tickers.TickersScreenView

fun MainViewController() = ComposeUIViewController {
    val tickersScreenView = initializeKoin().koin.get<TickersScreenView>()
    App(tickersScreenView)
}