package com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.state

import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class TakenTransportationState {
    object Idle: TakenTransportationState()
    object Loading: TakenTransportationState()
    data class TakenTransportFetched(val transportOrders :List<TransportationOrder>): TakenTransportationState()
    data class Error(val error:Failure): TakenTransportationState()

}