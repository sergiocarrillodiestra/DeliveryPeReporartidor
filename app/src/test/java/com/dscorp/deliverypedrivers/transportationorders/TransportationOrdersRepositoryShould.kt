package com.dscorp.deliverypedrivers.transportationorders

import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.data.TransportationOrdersService
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class TransportationOrdersRepositoryShould : BaseUnitTest() {

    private val newTransportationsOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(newTransportationsOrders)
    private val driverId = "computer"
    private val service: TransportationOrdersService = mock()
    private lateinit var repository: TransportationOrdersRepository
    private val exception = RuntimeException("something went wrong")

    @Before
    fun setUp() {
        repository = TransportationOrdersRepository(service)
    }

    @Test
    fun `get new transportation orders from service`() {
        repository.getNewTransportationOrders(driverId)
        verify(service, times(1)).getNewTransportationOrders(driverId)
    }

    @Test
    fun `emit transportation orders from service`() = runBlockingTest {
        whenever(service.getNewTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )
        repository.getNewTransportationOrders(driverId)
        assertEquals(
            newTransportationsOrders,
            repository.getNewTransportationOrders(driverId).first().getOrNull()
        )


    }

    @Test
    fun `propagate errors`() = runBlockingTest {
        whenever(service.getNewTransportationOrders(driverId)).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        service.getNewTransportationOrders(driverId)
        assertEquals(exception, repository.getNewTransportationOrders(driverId).first().exceptionOrNull())
    }


}