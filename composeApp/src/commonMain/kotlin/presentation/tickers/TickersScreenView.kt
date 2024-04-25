package presentation.tickers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TickersScreenView(private val viewModel: TickersScreenViewModel) {

    @Composable
    fun render() {
        val tickers = viewModel.tickers.collectAsState(initial = TickersScreenUiState.Loading)

        when (tickers.value) {
            is TickersScreenUiState.Loading -> Text("Loading...")
            is TickersScreenUiState.Success -> {
                val successState = tickers.value as TickersScreenUiState.Success
                TickersList(successState.tickers)
            }

            is TickersScreenUiState.Error -> {
                val errorState = tickers.value as TickersScreenUiState.Error
                Text("Error: ${errorState.message}")
            }
        }
    }

    @Composable
    private fun TickersList(tickers: List<TickerUiModel>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            items(items = tickers) { ticker ->
                TickerItem(ticker)
            }
        }
    }

    @Composable
    private fun TickerItem(ticker: TickerUiModel) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .shadow(4.dp)
                .clickable { println("DBG: clicked $ticker") },
            border = BorderStroke(0.2.dp, Color.Gray),
            elevation = 8.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = ticker.symbol,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    maxLines = 1
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = ticker.rate.toString(),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.End,
                        maxLines = 1
                    )

                    Text(
                        text = ticker.dailyChange,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                        color = ticker.dailyChangeColor,
                        fontSize = 14.sp,
                    )

                }

            }
        }
    }
}