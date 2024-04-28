package domain

import domain.model.Ticker

class GetTickersUseCase(private val cryptoRepository: CryptoRepository) {
    suspend fun execute(): List<Ticker> = cryptoRepository.getTickers()
}