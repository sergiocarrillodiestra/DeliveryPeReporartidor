package com.dscorp.deliverypedrivers.transportationorders

import com.dscorp.deliverypedrivers.Constants
import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.data.TransportationOrdersService
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.presentation.transportationOrders.TransportationOrdersViewModel
import com.dscorp.deliverypedrivers.utils.BaseUnitTest
import com.dscorp.deliverypedrivers.utils.captureValues
import com.dscorp.deliverypedrivers.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class TransportationOrdersViewModelShould : BaseUnitTest() {

    lateinit var viewModel: TransportationOrdersViewModel
    private val newTransportationsOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(newTransportationsOrders)
    private val driverId: String = "computer"
    private val service: TransportationOrdersService = mock()
    private val repository: TransportationOrdersRepository = mock()
    private val exception = Exception("something went wrong")

    @Before
    fun setup() {
        viewModel = TransportationOrdersViewModel(repository)
    }


    @Test
    fun `get new transportation orders from repository`() = runBlockingTest {
        viewModel.getNewTransportationOrders(driverId)
        verify(repository, times(1)).getNewTransportationOrders(driverId)
    }

    @Test
    fun `get new transportation orders from service`() {
        val myrepo = TransportationOrdersRepository(service)
        TransportationOrdersViewModel(myrepo).getNewTransportationOrders(driverId)
        verify(service, times(1)).getNewTransportationOrders(driverId)
    }

    @Test
    fun `emits new tansportation orders from repository`() {
        whenever(repository.getNewTransportationOrders(driverId))
            .thenReturn(
                flow {
                    emit(expected)
                }
            )

        viewModel.getNewTransportationOrders(driverId)
        assertEquals(expected, viewModel.newTransportationOrders.getValueForTest())
    }


    @Test
    fun `throw error when driverid is empty`() = runBlockingTest {
        viewModel.getNewTransportationOrders("")
        assertEquals(
            Constants.EMPTY_STRING_ERROR,
            viewModel.newTransportationOrders.getValueForTest()!!.exceptionOrNull()!!.message
        )
    }


    @Test
    fun emitErrorWhenReceiveError() {
        whenever(repository.getNewTransportationOrders(driverId)).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        viewModel.getNewTransportationOrders(driverId)
        assertEquals(
            exception,
            viewModel.newTransportationOrders.getValueForTest()?.exceptionOrNull()
        )

    }

    @Test
    fun `show loader while loading`() = runBlockingTest {
        whenever(repository.getNewTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel.loader.captureValues {
            viewModel.getNewTransportationOrders(driverId)
            viewModel.newTransportationOrders.getValueForTest()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun `close Loader After Playlist Load`() = runBlockingTest {
        whenever(repository.getNewTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel.loader.captureValues {

            viewModel.getNewTransportationOrders(driverId)
            viewModel.newTransportationOrders.getValueForTest()
            assertEquals(false, values.last())
        }
    }

}