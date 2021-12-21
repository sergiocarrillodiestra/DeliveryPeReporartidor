package com.dscorp.deliverypedrivers.presentation.bottomnav.takenorders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscorp.deliverypedrivers.data.TransportationOrdersRepository
import com.dscorp.deliverypedrivers.domain.TransportationOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakenTransportationViewModel @Inject constructor(private val repository: TransportationOrdersRepository) :
    ViewModel() {
    val loader = MutableLiveData<Boolean>()
    private val _takedTransportationOrders = MutableLiveData<Result<List<TransportationOrder>>>()
    val takedTransportationOrders: LiveData<Result<List<TransportationOrder>>> get() = _takedTransportationOrders

    fun getTakenTransportationOrders(driverId: String) {

        viewModelScope.launch {
            repository.getTakenTransportationOrders(driverId)
                .onStart { loader.value = true }
                .onEach { loader.value = false }
                .catch { ex -> _takedTransportationOrders.postValue(Result.failure(ex)) }
                .collect { result ->
                    _takedTransportationOrders.postValue(result)
                }

        }

    }

}

