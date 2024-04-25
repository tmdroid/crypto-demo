package data.remote.bitfinex

import data.client
import data.remote.bitfinex.dto.BitfinexTickersResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class BitfinexServiceTest {

    private lateinit var service: BitfinexService

    @BeforeTest
    fun setup() {
        service = RemoteBitfinexServiceImpl(client)
    }

    @Test
    fun `test network request`() = runTest {
        val data = service.getTickers(SYMBOLS.split(","))
        assertTrue { data.data.isNotEmpty() }
    }

    companion object {
        private const val SYMBOLS =
            "tBTCUSD,tETHUSD,tCHSB:USD,tLTCUSD,tXRPUSD,tDSHUSD,tRRTUSD,tEOSUSD,tSANUSD,tDATUSD,tSNTUSD,tDOGE:USD,tLUNA:USD,tMATIC:USD,tNEXO:USD,tOCEAN:USD,tBEST:USD,tAAVE:USD,tPLUUSD,tFILUSD"
    }
}