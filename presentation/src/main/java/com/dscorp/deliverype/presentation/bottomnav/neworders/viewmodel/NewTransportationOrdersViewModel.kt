package com.dscorp.deliverype.presentation.bottomnav.neworders.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.data.TransportationOrdersRepository
import com.dscorp.deliverype.domain.TransportationOrder
import com.dscorp.deliverype.presentation.bottomnav.neworders.intent.NewTransportationIntent
import com.dscorp.deliverype.presentation.bottomnav.neworders.state.NewTransportationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransportationOrdersViewModel @Inject constructor(
    private val repository: TransportationOrdersRepository
) : ViewModel() {

    val newTransportationIntet = Channel<NewTransportationIntent>(Channel.UNLIMITED)

    private val _transportationOrderState =
        MutableStateFlow<NewTransportationState>(NewTransportationState.Idle)
    val transportationOrderState: StateFlow<NewTransportationState> get() = _transportationOrderState

    val loader = MutableLiveData<Boolean>()
    private val _newTransportationOrders = MutableLiveData<Result<List<TransportationOrder>>>()

    val newTransportationOrders: LiveData<Result<List<TransportationOrder>>> get() = _newTransportationOrders

    init {

    }

    private fun handleIntent() {
        viewModelScope.launch {
            newTransportationIntet.consumeAsFlow().collect {
                it
                when (it) {
                    is NewTransportationIntent.FetchNewTransportations -> fetchNewTransportationOrders()
                }
            }
        }
    }

    private fun fetchNewTransportationOrders() {
        viewModelScope.launch {
            _transportationOrderState.value = NewTransportationState.Loading
            _transportationOrderState.value = try {
                NewTransportationState.LoadNewTransportations(repository.getNewTransportationOrders())
            } catch (e: Exception) {
                NewTransportationState.Error(e.message)
            }
        }
    }

    fun getNewTransportationOrders() {

        viewModelScope.launch {
            repository.getNewTransportationOrders()
                .onStart {
                    //MOSTRAR UI DE CARGA
                    loader.value = true
                }
                .onEach {
                    loader.value = false
                }
                .catch { ex ->
                    _newTransportationOrders.postValue(Result.failure(ex))
                }
                .collect {
                    _newTransportationOrders.postValue(it)
                }
        }
    }

}
