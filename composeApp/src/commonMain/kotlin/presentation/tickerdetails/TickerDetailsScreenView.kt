package presentation.tickerdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cryptomarketplaceapp.composeapp.generated.resources.Res
import cryptomarketplaceapp.composeapp.generated.resources.back
import cryptomarketplaceapp.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.ScreenView
import presentation.common.ErrorRow
import presentation.common.LoadingRow
import presentation.common.Toolbar

@OptIn(ExperimentalResourceApi::class)
class TickerDetailsScreenView(
    private val viewModel: TickerDetailsViewModel
) : ScreenView() {

    @Composable
    fun render(symbol: String, onBackClicked: () -> Unit) {
        val tickerDetails = viewModel.getUiState(symbol = symbol)
            .collectAsState(initial = TickerDetailsUiState.Loading)

        Column(modifier = Modifier.fillMaxSize()) {
            Toolbar(
                title = symbol,
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(resource = Res.string.back)
                        )
                    }
                },
            )

            renderContent(tickerDetails)
        }
    }

    @Composable
    private fun renderContent(state: State<TickerDetailsUiState>) {
        when (state.value) {
            is TickerDetailsUiState.Loading -> {
                LoadingRow(text = stringResource(resource = Res.string.loading))
            }

            is TickerDetailsUiState.Success -> {
                val ticker = (state.value as TickerDetailsUiState.Success).ticker
                Text(
                    text = "Here the details for $ticker will be loaded...",
                    modifier = Modifier.padding(16.dp),
                )
            }

            is TickerDetailsUiState.Error -> {
                val errorState = state.value as TickerDetailsUiState.Error
                ErrorRow(message = errorState.message)
            }
        }
    }

}