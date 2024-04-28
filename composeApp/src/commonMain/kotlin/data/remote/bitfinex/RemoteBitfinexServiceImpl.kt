package data.remote.bitfinex

import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteBitfinexServiceImpl(
    private val client: HttpClient
) : BitfinexService {
    override suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto {
        val joinedSymbols = symbols.joinToString(",")
        val symbolsQuery = "$SYMBOLS_QUERY=$joinedSymbols"
        return client.get("$BASE_PATH/$symbolsQuery").body()
    }

    companion object {
        private const val BASE_PATH = "https://api-pub.bitfinex.com/v2/tickers"
        private const val SYMBOLS_QUERY = "?symbols"
    }
}