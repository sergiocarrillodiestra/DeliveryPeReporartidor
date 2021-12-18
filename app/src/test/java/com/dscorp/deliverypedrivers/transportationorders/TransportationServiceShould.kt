package com.dscorp.deliverypedrivers.transportationorders

import com.dscorp.deliverypedrivers.data.TransportationOrderAPI
import com.dscorp.deliverypedrivers.data.TransportationOrdersService
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class TransportationServiceShould : BaseUnitTest() {

    private val exception = RuntimeException("network error")
    private val transportationOrders: List<TransportationOrder> = mock()
    private val driverId: String = "computer"
    private lateinit var service: TransportationOrdersService
    private val api: TransportationOrderAPI = mock()

    @Before
    fun setup() {
        service = TransportationOrdersService(api)
    }


    @Test
    fun `get new transportation order from api`() = runBlockingTest {
        service.getNewTransportationOrders(driverId).first()
        verify(api, times(1)).getNewTransportationOrders(driverId)
    }

    @Test
    fun `convert values to flow result and emits them`() = runBlockingTest {
        whenever(api.getNewTransportationOrders(driverId)).thenReturn(transportationOrders)
        assertEquals(
            Result.success(transportationOrders),
            service.getNewTransportationOrders(driverId).first()
        )

    }

    @Test
    fun `emit error when network fails`() = runBlockingTest {
        whenever(api.getNewTransportationOrders(driverId)).thenThrow(exception)
        assertEquals(
            exception,
            service.getNewTransportationOrders(driverId).first().exceptionOrNull()
        )
    }

}