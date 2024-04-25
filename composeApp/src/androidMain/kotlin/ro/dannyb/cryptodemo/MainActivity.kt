package ro.dannyb.cryptodemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import di.initializeKoin
import presentation.App
import presentation.tickers.TickersScreenView
import util.getViewModel

class MainActivity : ComponentActivity() {

    private val koinApplication by lazy { initializeKoin() }

    private val tickersScreenView by lazy { koinApplication.getViewModel<TickersScreenView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(tickersScreenView)
        }
    }
}