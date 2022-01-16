package com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.state

import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder

sealed class NewTransportationIntentStates {
    object Idle : NewTransportationIntentStates()
    object Loading : NewTransportationIntentStates()
    data class LoadNewTranspOrdersIntent(val transportationsOrders: List<TransportationOrder>) : NewTransportationIntentStates()
    data class Error(val error: Failure) : NewTransportationIntentStates()
}