package data.remote.bitfinex

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import data.mapper.DataMapper
import domain.model.Ticker

class BitfinanceTickerDtoToDomainTickerMapper :
    DataMapper<BitfinexTickersResponseDto, List<Ticker>> {

    override fun map(input: BitfinexTickersResponseDto): List<Ticker> {
        return input.data.mapNotNull {
            try {
                val symbol = checkNotNull(it.symbol)
                    .removePrefix("f")
                    .removePrefix("t")
                    .removeSuffix("USD")
                    .removeSuffix(":")

                Ticker(
                    symbol = symbol,
                    icon = symbol.toLowerCase(Locale.current).let { "$it.svg" },
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
