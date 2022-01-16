package com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.domain.usecases.GetTakenTransportationOrdersUseCase
import com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.intent.TakenTransportationIntent
import com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.state.TakenTransportationState
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
                    is TakenTransportationIntent.FetchTakenTransportationOrders -> fetchTakenTranspOrders(it.driver_id)
                }
            }
        }
    }

    private fun fetchTakenTranspOrders(driver_id: String) {
        viewModelScope.launch {
            _intentState.value = TakenTransportationState.Loading
            getTakenTransportationOrdersUseCase.invoke(driver_id).either(
                {
                    _intentState.value = TakenTransportationState.Error(it)
                }, {
                    _intentState.value = TakenTransportationState.TakenTransportFetched(it)
                })
        }
    }

}

