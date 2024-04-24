package data.remote.bitfinex

import data.remote.bitfinex.dto.BitfinexTickersResponseDto

interface BitfinexService {
    suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto
}