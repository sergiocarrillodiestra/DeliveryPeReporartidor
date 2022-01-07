package com.dscorp.deliverype.presentation.features.bottomnav.neworders.state

import com.dscorp.deliverype.domain.entity.TransportationOrder

sealed class NewTransportationState {
    object Idle : NewTransportationState()
    object Loading : NewTransportationState()
    data class LoadNewTranspOrders(val transportationsOrders: List<TransportationOrder>) : NewTransportationState()
    data class Error(val error: String?) : NewTransportationState()
}