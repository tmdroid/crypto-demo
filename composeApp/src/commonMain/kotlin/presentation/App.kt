package presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.tickers.TickersScreenView

@Composable
@Preview
fun App(tickersScreenView: TickersScreenView) {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            tickersScreenView.render()
        }
    }
}