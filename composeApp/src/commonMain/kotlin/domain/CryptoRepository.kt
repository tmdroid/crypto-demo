package domain

import domain.model.Ticker

interface CryptoRepository {

    suspend fun getTickers(symbols: List<String>): List<Ticker>

}