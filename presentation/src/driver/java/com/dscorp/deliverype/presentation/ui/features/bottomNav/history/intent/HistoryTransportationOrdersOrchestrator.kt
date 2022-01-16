package com.dscorp.deliverype.presentation.ui.features.bottomNav.history.intent

import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.ui.features.bottomNav.history.state.HistoryTransportationState
import com.dscorp.deliverype.presentation.ui.features.config.BaseIntentCallback

/**
 * Created by Sergio Carrillo Diestra on 15/01/2022.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 * Huacho, Peru.
 *
 **/
object HistoryTransportationOrdersOrchestrator {

    private lateinit var callback: HistoryTransportationCallback

    fun instance(callback: HistoryTransportationCallback): HistoryTransportationOrdersOrchestrator {
        this.callback = callback
        return this
    }

    fun orchestrate(intentState: HistoryTransportationState) {
        when (intentState) {
            is HistoryTransportationState.Error -> callback.onError(intentState.error)
            is HistoryTransportationState.LoadTransportOrders -> callback.onHistoryTransportationOrdersFetched(
                intentState.transportOrders
            )
            HistoryTransportationState.Loading -> callback.onLoading()
        }
    }

    interface HistoryTransportationCallback : BaseIntentCallback {
        fun onHistoryTransportationOrdersFetched(orders: List<TransportationOrder>)
    }

}