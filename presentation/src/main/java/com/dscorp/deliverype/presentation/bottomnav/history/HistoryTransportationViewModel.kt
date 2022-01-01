package com.dscorp.deliverype.presentation.bottomnav.history

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
class HistoryTransportationViewModel @Inject constructor(private val repository: TransportationOrdersRepository) :
    ViewModel() {

    private val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean> get() = _loader

    private val _historyOrders = MutableLiveData<Result<List<TransportationOrder>>>()
    val historyOrders: LiveData<Result<List<TransportationOrder>>> get() = _historyOrders

    fun getHistoryOfTransportationOrders(driverId: String) {
        viewModelScope.launch {
            repository.getHistoryOfTransportationOrders(driverId)
                .onStart { _loader.postValue(true) }
                .onEach {
                    _loader.postValue(false)
                }
                .catch { ex -> _historyOrders.postValue(Result.failure(ex)) }

                .collect {
                    _historyOrders.postValue(it)
                }
        }

    }


}
