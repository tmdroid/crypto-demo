package domain

import domain.model.Ticker

interface CryptoRepository {
    suspend fun getTickers(): List<Ticker>
}