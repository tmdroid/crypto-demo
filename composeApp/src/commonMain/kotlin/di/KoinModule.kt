package di

import data.client
import data.remote.CryptoRepositoryImpl
import data.remote.bitfinex.BitfinexService
import data.remote.bitfinex.RemoteBitfinexServiceImpl
import data.remote.mapper.BitfinanceTickerDtoToDomainTickerMapper
import domain.CryptoRepository
import domain.GetTickersUseCase
import io.ktor.client.HttpClient
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.tickers.DomainTickerToTickerUiModelMapper
import presentation.tickers.TickersScreenView
import presentation.tickers.TickersScreenViewModel

val appModule = module {

    factory<HttpClient> { client }

    factory<CryptoRepository> { CryptoRepositoryImpl(get(), get()) }

    factory { DomainTickerToTickerUiModelMapper() }

    factory { GetTickersUseCase(get()) }

    factory { TickersScreenViewModel(get(), get()) }

    factory { TickersScreenView(get()) }
}

val bitfinexModule = module {

    factory<BitfinexService> { RemoteBitfinexServiceImpl(get()) }

    factory { BitfinanceTickerDtoToDomainTickerMapper() }

}

fun initializeKoin(): KoinApplication = startKoin { modules(appModule, bitfinexModule) }