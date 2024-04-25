package presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

abstract class CryptoViewModel {
    protected val viewModelScope get() = CoroutineScope(Dispatchers.IO)
}