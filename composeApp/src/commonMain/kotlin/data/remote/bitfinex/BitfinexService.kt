package data.remote.bitfinex

import data.remote.CryptoService
import data.remote.bitfinex.dto.BitfinexTickersResponseDto

interface BitfinexService : CryptoService<BitfinexTickersResponseDto> {
    override suspend fun getTickers(symbols: List<String>): BitfinexTickersResponseDto
}