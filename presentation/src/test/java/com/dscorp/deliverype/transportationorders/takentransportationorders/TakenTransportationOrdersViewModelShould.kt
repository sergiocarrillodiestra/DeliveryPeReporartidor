package com.dscorp.deliverype.transportationorders.takentransportationorders

import com.dscorp.deliverype.data.repository.TransportationOrdersRepositoryImpl
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.TakenTransportationViewModel
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


class TakenTransportationOrdersViewModelShould : BaseUnitTest() {

    private val takenTransportationOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(takenTransportationOrders)
    private val driverId = "computer"
    private lateinit var viewModel: TakenTransportationViewModel
    private val repositoryImpl: TransportationOrdersRepositoryImpl = mock()
    private val exception = Exception("something went wrong")

    @Before
    fun setup() {
        viewModel = TakenTransportationViewModel(repositoryImpl)
    }


    @Test
    fun `get taken transportation orders`() = runBlockingTest {
        viewModel.getTakenTransportationOrders(driverId)
        verify(repositoryImpl, times(1)).getTakenTransportationOrders(driverId)
    }


    @Test
    fun `emits taken transportation orders from repository`() = runBlockingTest {
        whenever(repositoryImpl.getTakenTransportationOrders(driverId)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel.getTakenTransportationOrders(driverId)
        assertEquals(expected, viewModel.takedTransportationOrders.getValueForTest())

    }

    @Test
    fun `emit when error received`() {
        whenever(repositoryImpl.getTakenTransportationOrders(driverId)).thenReturn(
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
        whenever(repositoryImpl.getTakenTransportationOrders(driverId)).thenReturn(
            flow { emit(expected) }
        )
        viewModel.loader.captureValues {
            viewModel.getTakenTransportationOrders(driverId)
            assertEquals(true, values.first())
        }

    }


    @Test
    fun `hide loader after the taken transport orders were loaded`() {
        whenever(repositoryImpl.getTakenTransportationOrders(driverId)).thenReturn(
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