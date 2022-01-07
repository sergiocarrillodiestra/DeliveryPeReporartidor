package com.dscorp.deliverype.presentation.features.bottomnav.takenorders.state

import com.dscorp.deliverype.domain.entity.TransportationOrder
import java.lang.Exception

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class TakenTransportationState {

    object Idle:TakenTransportationState()
    object Loading:TakenTransportationState()
    data class LoadTransportOrders( val transportOrders :List<TransportationOrder>):TakenTransportationState()
    data class Error(val ex:String?):TakenTransportationState()

}