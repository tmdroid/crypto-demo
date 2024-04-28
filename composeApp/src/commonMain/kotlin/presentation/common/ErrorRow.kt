package presentation.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cryptomarketplaceapp.composeapp.generated.resources.Res
import cryptomarketplaceapp.composeapp.generated.resources.generic_error
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ErrorRow(message: String) {
    Text(
        text = stringResource(resource = Res.string.generic_error, message),
        modifier = Modifier.fillMaxSize().padding(32.dp),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}