package presentation.tickers

import data.mapper.DataMapper
import domain.model.Ticker
import theme.Colors
import util.roundStringToDecimals
import kotlin.math.abs

class DomainTickerToTickerUiModelMapper : DataMapper<List<Ticker>, List<TickerUiModel>> {
    override fun map(input: List<Ticker>): List<TickerUiModel> = input.map {
        TickerUiModel(
            iconUrl = "${COIN_ICON_BASE_URL}/${it.icon}",
            symbol = it.symbol,
            rate = it.lastPrice,
            dailyChange = it.dailyChangePerc.let {
                buildString {
                    if (it > 0) append("+") else append("-")
                    append(abs(it * 100).toFloat().roundStringToDecimals(2))
                    append("%")
                }
            },
            dailyChangeColor = if (it.dailyChangePerc > 0) Colors.COLOR_GREEN else Colors.COLOR_RED
        )
    }

    companion object {
        private const val COIN_ICON_BASE_URL =
            "https://raw.githubusercontent.com/Cryptofonts/cryptoicons/master/SVG"
    }
}