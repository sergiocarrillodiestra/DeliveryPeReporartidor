package com.dscorp.deliverype.presentation.bottomnav.neworders.state

import com.dscorp.deliverype.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow

sealed class NewTransportationState {
    object Idle:NewTransportationState()
    object Loading:NewTransportationState()
    data class LoadNewTransportations(val transportationsOrders: Flow<Result<List<TransportationOrder>>>):NewTransportationState()
    data class Error(val error:String?):NewTransportationState()
}