package com.dscorp.deliverype.presentation.ui.features.bottomNav.history.state

import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class HistoryTransportationState {
    object Idle : HistoryTransportationState()
    object Loading : HistoryTransportationState()
    data class LoadTransportOrders(val transportOrders: List<TransportationOrder>) :
        HistoryTransportationState()
    data class Error(val error: Failure) : HistoryTransportationState()
}