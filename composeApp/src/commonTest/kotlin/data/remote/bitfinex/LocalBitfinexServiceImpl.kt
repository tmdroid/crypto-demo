package data.remote.bitfinex

import data.remote.bitfinex.dto.BitfinexTickerDataDto
import data.remote.bitfinex.dto.BitfinexTickersResponseDto

class LocalBitfinexServiceImpl : BitfinexService {
    override suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto {
        val values = listOf(
            BitfinexTickerDataDto(
                "tBTCUSD",
                65907.0,
                6.22136307,
                65908.0,
                5.49994209,
                -718.0,
                -0.01077366,
                65926.0,
                512.36852112,
                67233.0,
                65783.0
            )
        )

        return BitfinexTickersResponseDto(values)
    }
}