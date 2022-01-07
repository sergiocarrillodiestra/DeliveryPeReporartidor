package com.dscorp.deliverype.presentation.features.bottomnav.history.state

import com.dscorp.deliverype.domain.entity.TransportationOrder
import java.lang.Exception

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class HistoryTransportationState {

    object Idle:HistoryTransportationState()
    object Loading:HistoryTransportationState()
    data class LoadTransportOrders( val transportOrders :List<TransportationOrder>):HistoryTransportationState()
    data class Error(val message:String?):HistoryTransportationState()

}