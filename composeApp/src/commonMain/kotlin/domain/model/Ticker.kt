package domain.model

data class Ticker(
    val symbol: String,
    val bid: Double,
    val ask: Double,
    val dailyChangePerc: Double,
    val lastPrice: Double,
    val volume: Double,
    val high: Double,
    val low: Double
)