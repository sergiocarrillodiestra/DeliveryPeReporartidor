package com.dscorp.deliverype.transportationorders

import com.dscorp.deliverype.data.TransportationOrdersRepository
import com.dscorp.deliverype.data.TransportationOrdersService
import com.dscorp.deliverype.domain.TransportationOrder
import com.dscorp.deliverype.utils.BaseUnitTest
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
    private val service: TransportationOrdersService = mock()
    private lateinit var repository: TransportationOrdersRepository
    private val exception = RuntimeException("something went wrong")

    @Before
    fun setUp() {
        repository = TransportationOrdersRepository(service)
    }

    @Test
    fun `get new transportation orders from service`() {
        repository.getNewTransportationOrders()
        verify(service, times(1)).getNewTransportationOrders()
    }

    @Test
    fun `emit new transportation orders from service`() = runBlockingTest {
        whenever(service.getNewTransportationOrders()).thenReturn(
            flow {
                emit(expected)
            }
        )
        repository.getNewTransportationOrders()
        assertEquals(
            newTransportationsOrders,
            repository.getNewTransportationOrders().first().getOrNull()
        )
    }




    @Test
    fun `propagate errors`() = runBlockingTest {
        whenever(service.getNewTransportationOrders()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        service.getNewTransportationOrders()
        assertEquals(exception, repository.getNewTransportationOrders().first().exceptionOrNull())
    }


}