package presentation.tickers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cryptomarketplaceapp.composeapp.generated.resources.Res
import cryptomarketplaceapp.composeapp.generated.resources.filter
import cryptomarketplaceapp.composeapp.generated.resources.loading
import cryptomarketplaceapp.composeapp.generated.resources.token_icon_description
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.ScreenView
import presentation.common.ErrorRow
import presentation.common.LoadingRow
import presentation.common.Toolbar

@OptIn(ExperimentalResourceApi::class)
class TickersScreenView(
    private val viewModel: TickersScreenViewModel,
) : ScreenView() {

    @Composable
    fun render(scrollState: LazyListState, onTickerClicked: (String) -> Unit) {
        val uiState = viewModel.uiState.collectAsState(initial = TickersScreenUiState.Loading)

        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(
                searchQuery = viewModel.searchQuery.collectAsState().value,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onSearchModeChange = viewModel::onSearchModeChange,
                inSearchMode = viewModel.searchMode.collectAsState().value,
                actions = {
                    IconButton(onClick = {
                        viewModel.onSearchModeChange(true)
                        viewModel.onSearchQueryChange(null)
                    }) {
                        Icon(
                            imageVector = Icons.Sharp.Search,
                            contentDescription = stringResource(resource = Res.string.filter)
                        )
                    }
                }
            )

            renderContent(scrollState, uiState, onTickerClicked)
        }
    }

    @Composable
    private fun renderContent(
        scrollState: LazyListState,
        uiState: State<TickersScreenUiState>,
        onTickerClicked: (String) -> Unit
    ) {
        when (uiState.value) {
            is TickersScreenUiState.Loading -> {
                LoadingRow(text = stringResource(resource = Res.string.loading))
            }

            is TickersScreenUiState.Success -> {
                val successState = uiState.value as TickersScreenUiState.Success
                TickersList(scrollState, successState.tickers, onTickerClicked)
            }

            is TickersScreenUiState.Error -> {
                val errorState = uiState.value as TickersScreenUiState.Error
                ErrorRow(message = errorState.message)
            }
        }
    }

    @Composable
    private fun TickersList(
        scrollState: LazyListState,
        tickers: List<TickerUiModel>,
        onTickerClicked: (String) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp),
            state = scrollState,
        ) {
            items(items = tickers) { ticker ->
                TickerItem(ticker, onTickerClicked)
            }
        }
    }

    @Composable
    private fun TickerItem(ticker: TickerUiModel, onTickerClicked: (String) -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp)
                .clickable { onTickerClicked(ticker.symbol) },
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
            ) {

                KamelImage(
                    resource = asyncPainterResource(data = ticker.iconUrl),
                    contentDescription = stringResource(
                        resource = Res.string.token_icon_description,
                        ticker.symbol
                    ),
                    modifier = Modifier.size(32.dp).align(Alignment.CenterVertically),
                    alignment = Alignment.CenterStart,
                )

                Spacer(modifier = Modifier.size(16.dp))

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