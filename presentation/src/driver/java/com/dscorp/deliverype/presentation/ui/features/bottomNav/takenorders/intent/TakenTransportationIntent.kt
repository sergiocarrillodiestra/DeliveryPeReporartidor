package com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.intent

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class TakenTransportationIntent {
    data class FetchTakenTransportationOrders(val driver_id: String) : TakenTransportationIntent()
}