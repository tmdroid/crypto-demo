package ro.dannyb.cryptodemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import di.initializeKoin
import org.koin.core.KoinApplication
import presentation.App
import presentation.tickers.TickersScreenView

class MainActivity : ComponentActivity() {

    private val koinApplication by lazy { initializeKoin() }

    private val tickersScreenView by lazy { koinApplication.getViewModel<TickersScreenView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(tickersScreenView)
        }
    }

    private inline fun <reified T> KoinApplication.getViewModel(): T {
        return koin.get()
    }
}