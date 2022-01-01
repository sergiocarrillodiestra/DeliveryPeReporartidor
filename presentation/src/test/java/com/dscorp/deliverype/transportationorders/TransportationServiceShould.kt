package com.dscorp.deliverype.transportationorders

import com.dscorp.deliverype.data.TransportationOrderAPI
import com.dscorp.deliverype.data.TransportationOrdersService
import com.dscorp.deliverype.domain.TransportationOrder
import com.dscorp.deliverype.utils.BaseUnitTest
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
    private lateinit var service: TransportationOrdersService
    private val api: TransportationOrderAPI = mock()

    @Before
    fun setup() {
        service = TransportationOrdersService(api)
    }


    @Test
    fun `get new transportation order from api`() = runBlockingTest {
        service.getNewTransportationOrders().first()
        verify(api, times(1)).getNewTransportationOrders()
    }

    @Test
    fun `convert values to flow result and emits them`() = runBlockingTest {
        whenever(api.getNewTransportationOrders()).thenReturn(transportationOrders)
        assertEquals(
            Result.success(transportationOrders),
            service.getNewTransportationOrders().first()
        )

    }

    @Test
    fun `emit error when network fails`() = runBlockingTest {
        whenever(api.getNewTransportationOrders()).thenThrow(exception)
        assertEquals(
            exception,
            service.getNewTransportationOrders().first().exceptionOrNull()
        )
    }

}