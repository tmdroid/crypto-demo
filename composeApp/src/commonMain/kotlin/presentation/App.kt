package presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.tickerdetails.TickerDetailsScreenView
import presentation.tickers.TickersScreenView

@Composable
@Preview
fun App(
    tickersScreenView: TickersScreenView,
    tickerDetailsScreenView: TickerDetailsScreenView,
) {
    var currentAppScreen: AppScreen by remember { mutableStateOf(AppScreen.Tickers) }
    val scrollState = rememberLazyListState()

    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            when (currentAppScreen) {
                is AppScreen.Tickers -> tickersScreenView.render(scrollState) {
                    currentAppScreen = AppScreen.TickerDetails(it)
                }

                is AppScreen.TickerDetails -> {
                    val symbol = (currentAppScreen as AppScreen.TickerDetails).symbol
                    tickerDetailsScreenView.render(symbol) {
                        currentAppScreen = AppScreen.Tickers
                    }
                }
            }
        }
    }
}