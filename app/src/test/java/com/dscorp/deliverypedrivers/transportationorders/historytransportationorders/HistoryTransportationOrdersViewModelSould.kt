package com.dscorp.deliverypedrivers.transportationorders.historytransportationorders

import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.presentation.bottomnav.history.HistoryTransportationViewModel
import com.dscorp.deliverypedrivers.utils.BaseUnitTest
import com.dscorp.deliverypedrivers.utils.captureValues
import com.dscorp.deliverypedrivers.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class HistoryTransportationOrdersViewModelSould : BaseUnitTest() {
    private val exception = RuntimeException("something went wroing")
    private val transportatOrdersHistory = mock<List<TransportationOrder>>()
    private val expected = Result.success(transportatOrdersHistory)
    private val driverId = "computer"
    private val repository: TransportationOrdersRepository = mock()
    private lateinit var viewModel: HistoryTransportationViewModel

    @Before
    fun setup() {
        viewModel = HistoryTransportationViewModel(repository)
    }


    @Test
    fun `get history of transportation orders from repository`() = runBlockingTest {
        viewModel.getHistoryOfTransportationOrders(driverId)
        verify(repository, times(1)).getHistoryOfTransportationOrders(driverId)
    }

    @Test
    fun `emit history transportation orders from repository`() = runBlockingTest {
        whenever(repository.getHistoryOfTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModel.getHistoryOfTransportationOrders(driverId)
        Assert.assertEquals(expected, viewModel.historyOrders.getValueForTest())
    }

    @Test
    fun `emit error when receive error`() = runBlockingTest {
        whenever((repository.getHistoryOfTransportationOrders(driverId))).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        viewModel.getHistoryOfTransportationOrders(driverId)
        Assert.assertEquals(
            exception,
            viewModel.historyOrders.getValueForTest()!!.exceptionOrNull()
        )

    }

    @Test
    fun `show loader when fetching history transpor orders`() = runBlockingTest {
        whenever(repository.getHistoryOfTransportationOrders(driverId)).thenReturn(
            flow { emit(expected) }
        )
        viewModel.loader.captureValues {
            viewModel.getHistoryOfTransportationOrders(driverId)
            Assert.assertEquals(true, values.first())
        }

    }

    @Test
    fun `hide loader after history transpotation orders loaded`() = runBlockingTest {
        whenever(repository.getHistoryOfTransportationOrders(driverId)).thenReturn(
            flow { emit(expected) }
        )

        viewModel.loader.captureValues {
            viewModel.getHistoryOfTransportationOrders(driverId)
            Assert.assertEquals(false, values.last() )
        }

    }

}