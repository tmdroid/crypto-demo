package presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import cryptomarketplaceapp.composeapp.generated.resources.Res
import cryptomarketplaceapp.composeapp.generated.resources.app_name
import cryptomarketplaceapp.composeapp.generated.resources.back
import cryptomarketplaceapp.composeapp.generated.resources.filter
import cryptomarketplaceapp.composeapp.generated.resources.filter_tickers
import cryptomarketplaceapp.composeapp.generated.resources.search
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Toolbar(
    searchQuery: String,
    onSearchQueryChange: (String?) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var inFilterMode by remember { mutableStateOf(false) }

    if (inFilterMode) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    inFilterMode = false
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Sharp.ArrowBack,
                        contentDescription = stringResource(resource = Res.string.back)
                    )
                }
            },
            title = {
                TextField(
                    value = searchQuery,
                    onValueChange = { onSearchQueryChange(it) },
                    label = { Text(text = stringResource(resource = Res.string.filter_tickers)) },
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Sharp.Search,
                            contentDescription = stringResource(resource = Res.string.search)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        textColor = MaterialTheme.colors.onSurface,
                    ),
                )
            },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            elevation = 8.dp,
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    } else {
        TopAppBar(
            title = { Text(stringResource(resource = Res.string.app_name)) },
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
            modifier = Modifier.fillMaxWidth().shadow(8.dp),
            actions = {
                IconButton(onClick = {
                    inFilterMode = true
                    onSearchQueryChange(null)
                }) {
                    Icon(
                        imageVector = Icons.Sharp.Search,
                        contentDescription = stringResource(resource = Res.string.filter)
                    )
                }
            }
        )

    }
}
