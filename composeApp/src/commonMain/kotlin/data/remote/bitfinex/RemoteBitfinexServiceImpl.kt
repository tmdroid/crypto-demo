package data.remote.bitfinex

import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteBitfinexServiceImpl(
    private val client: HttpClient
) : BitfinexService {
    override suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto {
        val symbolsQuery = "?symbols=${symbols.joinToString(",")}"
        return client.get("tickers/$symbolsQuery").body()
    }
}