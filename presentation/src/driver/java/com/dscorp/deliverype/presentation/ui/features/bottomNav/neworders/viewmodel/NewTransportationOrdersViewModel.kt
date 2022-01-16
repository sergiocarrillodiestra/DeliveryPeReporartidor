package com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.domain.usecases.GetNewTransportationOrdersUseCase
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.intent.NewTransportationIntents
import com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.state.NewTransportationIntentStates
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

    val userIntent = Channel<NewTransportationIntents>(Channel.UNLIMITED)

    private val _intentState =
        MutableStateFlow<NewTransportationIntentStates>(NewTransportationIntentStates.Idle)
   val intentIntentStates: StateFlow<NewTransportationIntentStates> get() = _intentState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is NewTransportationIntents.FetchNewTransportations -> fetchNewTransportationOrders()
                }
            }
        }
    }

    private fun fetchNewTransportationOrders() {
        viewModelScope.launch {
            _intentState.value = NewTransportationIntentStates.Loading
            getNewTransportationOrdersUseCase.invoke().either({
                _intentState.value = NewTransportationIntentStates.Error(it)
            }, {
                _intentState.value = NewTransportationIntentStates.LoadNewTranspOrdersIntent(it)
            })
        }
    }

}
