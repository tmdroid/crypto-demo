package ro.dannyb.cryptodemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import di.initializeKoin
import presentation.App
import presentation.tickerdetails.TickerDetailsScreenView
import presentation.tickers.TickersScreenView
import util.getScreenView

class MainActivity : ComponentActivity() {

    // Koin
    private val koinApplication by lazy { initializeKoin() }

    // Views
    private val tickersScreenView by lazy { koinApplication.getScreenView<TickersScreenView>() }
    private val tickerDetailsScreenView by lazy { koinApplication.getScreenView<TickerDetailsScreenView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(tickersScreenView, tickerDetailsScreenView)
        }
    }
}