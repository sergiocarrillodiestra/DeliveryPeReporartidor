package com.dscorp.deliverypedrivers.presentation.transportationOrders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverypedrivers.Constants
import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.RuntimeException
import javax.inject.Inject

@HiltViewModel
class TransportationOrdersViewModel @Inject constructor(
    private val repository: TransportationOrdersRepository
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    private val _newTransportationOrders = MutableLiveData<Result<List<TransportationOrder>>>()
    val newTransportationOrders: LiveData<Result<List<TransportationOrder>>> get() = _newTransportationOrders

    fun getNewTransportationOrders(driverId: String) {
        if (driverId.isNullOrEmpty()) {
            _newTransportationOrders.value = Result.failure(RuntimeException(Constants.EMPTY_STRING_ERROR))
        } else {
            viewModelScope.launch {
                repository.getNewTransportationOrders(driverId).onStart {
                    //MOSTRAR UI DE CARGA
                    loader.value=true
                }.onEach {
                    loader.value=false }
                    .catch { ex ->
                        _newTransportationOrders.postValue(Result.failure(ex))
                    }
                    .collect {
                        _newTransportationOrders.postValue(it)
                    }
            }
        }
    }

}
