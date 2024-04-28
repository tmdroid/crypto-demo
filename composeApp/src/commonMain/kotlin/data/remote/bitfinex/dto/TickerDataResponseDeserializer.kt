package data.remote.bitfinex.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeCollection
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

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: BitfinexTickersResponseDto) {
        encoder.encodeCollection(
            descriptor = listSerialDescriptor<BitfinexTickerDataDto>(),
            collectionSize = value.data.size,
        ) {
            value.data.forEach { dto ->
                dto.symbol?.let { encodeStringElement(descriptor, 0, it) }
                dto.bid?.let { encodeDoubleElement(descriptor, 1, it) }
                dto.bidSize?.let { encodeDoubleElement(descriptor, 2, it) }
                dto.ask?.let { encodeDoubleElement(descriptor, 3, it) }
                dto.askSize?.let { encodeDoubleElement(descriptor, 4, it) }
                dto.dailyChange?.let { encodeDoubleElement(descriptor, 5, it) }
                dto.dailyChangeRelative?.let { encodeDoubleElement(descriptor, 6, it) }
                dto.lastPrice?.let { encodeDoubleElement(descriptor, 7, it) }
                dto.volume?.let { encodeDoubleElement(descriptor, 8, it) }
                dto.high?.let { encodeDoubleElement(descriptor, 9, it) }
                dto.low?.let { encodeDoubleElement(descriptor, 10, it) }
            }
        }
    }
}