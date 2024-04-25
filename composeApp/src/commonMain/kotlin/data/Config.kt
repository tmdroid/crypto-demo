package data

import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import data.remote.bitfinex.dto.TickerDataResponseDeserializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import co.touchlab.kermit.Logger as KermitLogger

expect val httpEngine: HttpClientEngineFactory<*>

private val cryptoNetworkLogger by lazy { CryptoNetworkLogger() }

val client: HttpClient
    get() = HttpClient(httpEngine) {
        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
            logger = cryptoNetworkLogger
        }
        defaultRequest {
            header("Content-Type", "application/json")
            url("https://api-pub.bitfinex.com/v2/")
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                serializersModule = SerializersModule {
                    contextual(BitfinexTickersResponseDto::class, TickerDataResponseDeserializer)
                }
            })
        }
    }

private class CryptoNetworkLogger : Logger {
    override fun log(message: String) {
        KermitLogger.d(tag = "KtorClient", null) { message }
    }
}