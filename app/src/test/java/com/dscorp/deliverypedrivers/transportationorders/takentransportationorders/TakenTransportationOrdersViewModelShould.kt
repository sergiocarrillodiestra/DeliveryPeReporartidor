package com.dscorp.deliverypedrivers.transportationorders.takentransportationorders

import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import com.dscorp.deliverypedrivers.presentation.bottomnav.takenorders.TakenTransportationViewModel
import com.dscorp.deliverypedrivers.utils.BaseUnitTest
import com.dscorp.deliverypedrivers.utils.captureValues
import com.dscorp.deliverypedrivers.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class TakenTransportationOrdersViewModelShould : BaseUnitTest() {

    private val takenTransportationOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(takenTransportationOrders)
    private val driverId = "computer"
    private lateinit var viewModel: TakenTransportationViewModel
    private val repository: TransportationOrdersRepository = mock()
    private val exception = Exception("something went wrong")

    @Before
    fun setup() {
        viewModel = TakenTransportationViewModel(repository)
    }


    @Test
    fun `get taken transportation orders`() = runBlockingTest {
        viewModel.getTakenTransportationOrders(driverId)
        verify(repository, times(1)).getTakenTransportationOrders(driverId)
    }


    @Test
    fun `emits taken transportation orders from repository`() = runBlockingTest {
        whenever(repository.getTakenTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel.getTakenTransportationOrders(driverId)
        assertEquals(expected, viewModel.takedTransportationOrders.getValueForTest())

    }

    @Test
    fun `emit when error received`() {
        whenever(repository.getTakenTransportationOrders(driverId)).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        viewModel.getTakenTransportationOrders(driverId)
        assertEquals(
            exception,
            viewModel.takedTransportationOrders.getValueForTest()!!.exceptionOrNull()
        )

    }

    @Test
    fun `show loader while fetching the taken transportation orders`() {
        whenever(repository.getTakenTransportationOrders(driverId)).thenReturn(
            flow { emit(expected) }
        )
        viewModel.loader.captureValues {
            viewModel.getTakenTransportationOrders(driverId)
            assertEquals(true, values.first())
        }

    }


    @Test
    fun `hide loader after the taken transport orders were loaded`() {
        whenever(repository.getTakenTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel.loader.captureValues {
            viewModel.getTakenTransportationOrders(driverId)
            assertEquals(false, values.last())
        }
    }

}