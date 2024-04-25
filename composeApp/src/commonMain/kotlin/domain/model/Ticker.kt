package domain.model

import theme.Colors
import util.roundStringToDecimals
import kotlin.math.abs

data class Ticker(
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val dailyChangePerc: Double,
    val lastPrice: Double,
    val volume: Double,
    val high: Double,
    val low: Double
) {
    val dailyChangeString = dailyChangePerc.let {
        buildString {
            if (it > 0) append("+") else append("-")
            append(abs(it * 100).toFloat().roundStringToDecimals(2))
            append("%")
        }
    }

    val dailyChangeColor = if (dailyChangePerc > 0) Colors.COLOR_GREEN else Colors.COLOR_RED
}