package di

import data.client
import data.remote.CryptoDataSourcesConfig
import data.remote.CryptoDataSourcesConfig.DataSourceType
import data.remote.GetDataSourcesConfig
import data.remote.RemoteCryptoRepositoryImpl
import data.remote.SetDataSourcesConfig
import data.remote.bitfinex.BitfinexCryptoDataSource
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

    factory { BitfinexCryptoDataSource(bitfinexService = get(), mapper = get(), config = get()) }

    factory<CryptoRepository> {
        val dataSources = listOf(
            get<BitfinexCryptoDataSource>()
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
        CryptoDataSourcesConfig(
            mapOf(
                DataSourceType.BITFINEX to true
            )
        )
    }

    single<GetDataSourcesConfig> { get<CryptoDataSourcesConfig>() }

    single<SetDataSourcesConfig> { get<CryptoDataSourcesConfig>() }
}

val bitfinexModule = module {

    factory<BitfinexService> { RemoteBitfinexServiceImpl(client = get()) }

    factory { BitfinanceTickerDtoToDomainTickerMapper() }

}

fun initializeKoin(): KoinApplication = startKoin { modules(appModule, bitfinexModule) }