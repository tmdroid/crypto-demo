package data.remote

interface CryptoService<T> {
    suspend fun getTickers(symbols: List<String>): T
}