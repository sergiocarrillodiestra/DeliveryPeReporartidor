package com.dscorp.deliverype.presentation.features.bottomnav.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.usecases.GetHistoryTransportationOrdersUseCase
import com.dscorp.deliverype.presentation.features.bottomnav.history.intent.HistoryTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.history.state.HistoryTransportationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HistoryTransportationViewModel @Inject constructor(private val getHistoryTransportationOrdersUseCase: GetHistoryTransportationOrdersUseCase) :
        ViewModel() {

    val userIntent = Channel<HistoryTransportationIntent> { Channel.UNLIMITED }

    private val _intentState = MutableStateFlow<HistoryTransportationState>(HistoryTransportationState.Idle)
    val intentState: StateFlow<HistoryTransportationState> get() = _intentState


    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is HistoryTransportationIntent.fetchTakenTransportOrders -> fetchTakenTransportOrders(it.driver_id)
                }
            }
        }
    }

    private fun fetchTakenTransportOrders(driverId: String) {
        viewModelScope.launch {
            _intentState.value = HistoryTransportationState.Loading

            try {
                getHistoryTransportationOrdersUseCase.invoke(driverId).either(::handleError, ::handleSuccesHistoryTranspOrd)
            } catch (e: Exception) {
                _intentState.value = HistoryTransportationState.Error(e.message)
                e.printStackTrace()
            }
        }
    }

    private fun handleSuccesHistoryTranspOrd(list: List<TransportationOrder>) {
        _intentState.value = HistoryTransportationState.LoadTransportOrders(list)
    }

    fun handleError(e: Exception) {
        _intentState.value = HistoryTransportationState.Error(e.message)
        e.printStackTrace()
    }


}

