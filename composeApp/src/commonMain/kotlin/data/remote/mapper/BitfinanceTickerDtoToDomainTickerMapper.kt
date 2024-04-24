package data.remote.mapper

import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import domain.model.Ticker

class BitfinanceTickerDtoToDomainTickerMapper :
    DataMapper<BitfinexTickersResponseDto, List<Ticker>> {

    override fun map(input: BitfinexTickersResponseDto): List<Ticker> {
        return input.data.mapNotNull {
            try {
                Ticker(
                    symbol = checkNotNull(it.symbol),
                    bid = checkNotNull(it.bid),
                    ask = checkNotNull(it.ask),
                    dailyChangePerc = checkNotNull(it.dailyChangeRelative),
                    lastPrice = checkNotNull(it.lastPrice),
                    volume = checkNotNull(it.volume),
                    high = checkNotNull(it.high),
                    low = checkNotNull(it.low),
                )
            } catch (e: Exception) {
                null
            }
        }
    }

}
