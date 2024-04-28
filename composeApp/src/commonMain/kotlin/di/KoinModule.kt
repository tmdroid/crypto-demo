package di

import data.client
import data.remote.DataSourcesConfig
import data.remote.DataSourcesConfig.DataSourceType
import data.remote.RemoteCryptoRepositoryImpl
import data.remote.bitfinex.BitfinexDataSource
import data.remote.bitfinex.BitfinexService
import data.remote.bitfinex.RemoteBitfinexServiceImpl
import data.remote.bitfinex.BitfinanceTickerDtoToDomainTickerMapper
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

    factory { BitfinexDataSource(bitfinexService = get(), mapper = get(), config = get()) }

    factory<CryptoRepository> {
        val dataSources = listOf(
            get<BitfinexDataSource>()
        )
        RemoteCryptoRepositoryImpl(dataSources)
    }

    factory { DomainTickerToTickerUiModelMapper() }

    factory { GetTickersUseCase(cryptoRepository = get()) }

    factory {
        TickersScreenViewModel(
            getTickersUseCase = get(),
            domainTickerToTickerUiModelMapper = get()
        )
    }

    factory { TickersScreenView(viewModel = get()) }

    single {
        DataSourcesConfig(
            mapOf(
                DataSourceType.BITFINEX to true
            )
        )
    }
}

val bitfinexModule = module {

    factory<BitfinexService> { RemoteBitfinexServiceImpl(client = get()) }

    factory { BitfinanceTickerDtoToDomainTickerMapper() }

}

fun initializeKoin(): KoinApplication = startKoin { modules(appModule, bitfinexModule) }