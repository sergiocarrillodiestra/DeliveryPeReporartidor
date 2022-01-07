package com.dscorp.deliverype.presentation.features.bottomnav.takenorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.usecases.GetTakenTransportationOrdersUseCase
import com.dscorp.deliverype.presentation.features.bottomnav.takenorders.intent.TakenTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.takenorders.state.TakenTransportationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakenTransportationViewModel @Inject constructor(private val getTakenTransportationOrdersUseCase: GetTakenTransportationOrdersUseCase) :
        ViewModel() {


    val userIntent = Channel<TakenTransportationIntent> { Channel.UNLIMITED }

    private val _intentState = MutableStateFlow<TakenTransportationState>(TakenTransportationState.Idle)
    val intentState: StateFlow<TakenTransportationState> get() = _intentState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is TakenTransportationIntent.fetchTakenTransportOrders -> fetchTakenTranspOrders(it.driver_id)
                }
            }
        }
    }

    private fun fetchTakenTranspOrders(driver_id: String) {
        viewModelScope.launch {
            _intentState.value = TakenTransportationState.Loading
            try {
                getTakenTransportationOrdersUseCase.invoke(driver_id).either(::handleError, ::handleGetNewTransportationOrdSuccess)
            } catch (e: Exception) {
                _intentState.value= TakenTransportationState.Error(e.message)
                e.printStackTrace()
            }

        }
    }

    private fun handleGetNewTransportationOrdSuccess(docs: List<TransportationOrder>) {
        _intentState.value = TakenTransportationState.LoadTransportOrders(docs)
    }

    private fun handleError(ex: Exception) {
        _intentState.value = TakenTransportationState.Error(ex.message)
        ex.printStackTrace()
    }


}

