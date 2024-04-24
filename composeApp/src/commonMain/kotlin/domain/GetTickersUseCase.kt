package domain

import domain.model.Ticker

class GetTickersUseCase(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(symbols: List<String>): List<Ticker> = cryptoRepository.getTickers(symbols)
}