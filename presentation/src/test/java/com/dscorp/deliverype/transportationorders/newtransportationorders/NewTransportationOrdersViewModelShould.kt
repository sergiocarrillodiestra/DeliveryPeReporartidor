package com.dscorp.deliverype.transportationorders.newtransportationorders

import com.dscorp.deliverype.data.repository.TransportationOrdersRepositoryImpl
import com.dscorp.deliverype.data.TransportationOrdersService
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.viewmodel.NewTransportationOrdersViewModel
import com.dscorp.deliverype.utils.BaseUnitTest
import com.dscorp.deliverype.utils.captureValues
import com.dscorp.deliverype.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class NewTransportationOrdersViewModelShould : BaseUnitTest() {

    lateinit var viewModelNew: NewTransportationOrdersViewModel
    private val newTransportationsOrders = mock<List<TransportationOrder>>()
    private val expected = Result.success(newTransportationsOrders)
    private val service: TransportationOrdersService = mock()
    private val repositoryImpl: TransportationOrdersRepositoryImpl = mock()
    private val exception = Exception("something went wrong")

    @Before
    fun setup() {
        viewModelNew = NewTransportationOrdersViewModel(repositoryImpl)
    }


    @Test
    fun `get new transportation orders from repository`() = runBlockingTest {
        viewModelNew.getNewTransportationOrders()
        verify(repositoryImpl, times(1)).getNewTransportationOrders()
    }

    @Test
    fun `get new transportation orders from service`() {
        val myrepo = TransportationOrdersRepositoryImpl(service)
        NewTransportationOrdersViewModel(myrepo).getNewTransportationOrders()
        verify(service, times(1)).getNewTransportationOrders()
    }

    @Test
    fun `emits new tansportation orders from repository`() {
        whenever(repositoryImpl.getNewTransportationOrders())
            .thenReturn(
                flow {
                    emit(expected)
                }
            )

        viewModelNew.getNewTransportationOrders()
        assertEquals(expected, viewModelNew.newTransportationOrders.getValueForTest())
    }


//    @Test
//    fun `throw error when  is empty`() = runBlockingTest {
//        viewModel.getNewTransportationOrders()
//        assertEquals(
//            Constants.EMPTY_STRING_ERROR,
//            viewModel.newTransportationOrders.getValueForTest()!!.exceptionOrNull()!!.message
//        )
//    }


    @Test
    fun emitErrorWhenReceiveError() {
        whenever(repositoryImpl.getNewTransportationOrders()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )
        viewModelNew.getNewTransportationOrders()
        assertEquals(
            exception,
            viewModelNew.newTransportationOrders.getValueForTest()?.exceptionOrNull()
        )

    }

    @Test
    fun `show loader while loading`() = runBlockingTest {
        whenever(repositoryImpl.getNewTransportationOrders()).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModelNew.loader.captureValues {
            viewModelNew.getNewTransportationOrders()
            viewModelNew.newTransportationOrders.getValueForTest()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun `close Loader After Playlist Load`() = runBlockingTest {
        whenever(repositoryImpl.getNewTransportationOrders()).thenReturn(
            flow {
                emit(expected)
            }
        )
        viewModelNew.loader.captureValues {

            viewModelNew.getNewTransportationOrders()
            viewModelNew.newTransportationOrders.getValueForTest()
            assertEquals(false, values.last())
        }
    }

}