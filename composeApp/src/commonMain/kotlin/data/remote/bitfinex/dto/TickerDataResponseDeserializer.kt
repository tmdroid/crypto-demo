package data.remote.bitfinex.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

object TickerDataResponseDeserializer : KSerializer<BitfinexTickersResponseDto> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor(checkNotNull(BitfinexTickersResponseDto::class.simpleName))

    override fun deserialize(decoder: Decoder): BitfinexTickersResponseDto {
        val jsonDecoder =
            decoder as? JsonDecoder ?: throw SerializationException("Expected JsonDecoder")
        val items = jsonDecoder.decodeJsonElement() as? JsonArray

        val tickers = items?.map {
            val jsonElement = it.jsonArray

            BitfinexTickerDataDto(
                symbol = jsonElement[0].jsonPrimitive.contentOrNull,
                bid = jsonElement[1].jsonPrimitive.doubleOrNull,
                bidSize = jsonElement[2].jsonPrimitive.doubleOrNull,
                ask = jsonElement[3].jsonPrimitive.doubleOrNull,
                askSize = jsonElement[4].jsonPrimitive.doubleOrNull,
                dailyChange = jsonElement[5].jsonPrimitive.doubleOrNull,
                dailyChangeRelative = jsonElement[6].jsonPrimitive.doubleOrNull,
                lastPrice = jsonElement[7].jsonPrimitive.doubleOrNull,
                volume = jsonElement[8].jsonPrimitive.doubleOrNull,
                high = jsonElement[9].jsonPrimitive.doubleOrNull,
                low = jsonElement[10].jsonPrimitive.doubleOrNull,
            )
        } ?: emptyList()

        return BitfinexTickersResponseDto(data = tickers)
    }

    override fun serialize(encoder: Encoder, value: BitfinexTickersResponseDto) {
        TODO("Not yet implemented")
    }
}