package com.dscorp.deliverype.presentation.ui.features.bottomNav.history.intent

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class HistoryTransportationIntent {
    data class FetchHistoryTransportOrders(val driver_id: String) : HistoryTransportationIntent()
}