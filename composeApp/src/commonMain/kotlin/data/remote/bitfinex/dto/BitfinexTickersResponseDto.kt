package data.remote.bitfinex.dto

data class BitfinexTickersResponseDto(
    val data: List<BitfinexTickerDataDto>
)

data class BitfinexTickerDataDto(
    val symbol: String?,
    val bid: Double?,
    val bidSize: Double?,
    val ask: Double?,
    val askSize: Double?,
    val dailyChange: Double?,
    val dailyChangeRelative: Double?,
    val lastPrice: Double?,
    val volume: Double?,
    val high: Double?,
    val low: Double?
)
