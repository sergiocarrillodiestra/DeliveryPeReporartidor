package com.dscorp.deliverype.presentation.features.bottomnav.neworders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.usecases.GetNewTransportationOrdersUseCase
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.intent.NewTransportationIntent
import com.dscorp.deliverype.presentation.features.bottomnav.neworders.state.NewTransportationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransportationOrdersViewModel @Inject constructor(
        private val getNewTransportationOrdersUseCase: GetNewTransportationOrdersUseCase
) : ViewModel() {

    val userIntent = Channel<NewTransportationIntent>(Channel.UNLIMITED)

    private val _intentState = MutableStateFlow<NewTransportationState>(NewTransportationState.Idle)
    val intentState: StateFlow<NewTransportationState> get() = _intentState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is NewTransportationIntent.FetchNewTransportations -> fetchNewTransportationOrders()
                }
            }
        }
    }

    private fun fetchNewTransportationOrders() {
        viewModelScope.launch {
            _intentState.value = NewTransportationState.Loading
            try {
                getNewTransportationOrdersUseCase.invoke().either(::handleError, ::handleGetNewTransportationOrdSuccess)
            } catch (e: Exception) {
                _intentState.value = NewTransportationState.Error(e.message)
                e.printStackTrace()
            }
        }
    }

    private fun handleGetNewTransportationOrdSuccess(docs: List<TransportationOrder>) {
        _intentState.value = NewTransportationState.LoadNewTranspOrders(docs)
    }

    private fun handleError(ex: Exception) {
        _intentState.value = NewTransportationState.Error(ex.message)
        ex.printStackTrace()
    }

}
