package com.dscorp.deliverype.transportationorders

import com.dscorp.deliverype.data.repository.TransportationOrdersRepositoryImpl
import com.dscorp.deliverype.data.TransportationOrdersService
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class TransportationOrdersRepositoryImplShould : BaseUnitTest() {

    private val newTransportationsOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(newTransportationsOrders)
    private val service: TransportationOrdersService = mock()
    private lateinit var repositoryImpl: TransportationOrdersRepositoryImpl
    private val exception = RuntimeException("something went wrong")

    @Before
    fun setUp() {
        repositoryImpl = TransportationOrdersRepositoryImpl(service)
    }

    @Test
    fun `get new transportation orders from service`() {
        repositoryImpl.getNewTransportationOrders()
        verify(service, times(1)).getNewTransportationOrders()
    }

    @Test
    fun `emit new transportation orders from service`() = runBlockingTest {
        whenever(service.getNewTransportationOrders()).thenReturn(
            flow {
                emit(expected)
            }
        )
        repositoryImpl.getNewTransportationOrders()
        assertEquals(
            newTransportationsOrders,
            repositoryImpl.getNewTransportationOrders().first().getOrNull()
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
        assertEquals(exception, repositoryImpl.getNewTransportationOrders().first().exceptionOrNull())
    }


}