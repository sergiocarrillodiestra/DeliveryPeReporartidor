package com.dscorp.deliverype.presentation.bottomnav.neworders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverype.data.TransportationOrdersRepository
import com.dscorp.deliverype.domain.TransportationOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTransportationOrdersViewModel @Inject constructor(
    private val repository: TransportationOrdersRepository
) : ViewModel() {
    val loader = MutableLiveData<Boolean>()
    private val _newTransportationOrders = MutableLiveData<Result<List<TransportationOrder>>>()
    val newTransportationOrders: LiveData<Result<List<TransportationOrder>>> get() = _newTransportationOrders

    fun getNewTransportationOrders() {

        viewModelScope.launch {
            repository.getNewTransportationOrders()
                .onStart {
                //MOSTRAR UI DE CARGA
                loader.value = true  }
                .onEach {
                loader.value = false }
                .catch { ex ->
                    _newTransportationOrders.postValue(Result.failure(ex))  }
                .collect {
                    _newTransportationOrders.postValue(it)
                }
        }
    }

}
