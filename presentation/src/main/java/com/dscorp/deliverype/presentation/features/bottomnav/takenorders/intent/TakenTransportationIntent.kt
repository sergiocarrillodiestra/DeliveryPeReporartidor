package com.dscorp.deliverype.presentation.features.bottomnav.takenorders.intent

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class TakenTransportationIntent {
    data class fetchTakenTransportOrders(val driver_id: String) : TakenTransportationIntent()
}