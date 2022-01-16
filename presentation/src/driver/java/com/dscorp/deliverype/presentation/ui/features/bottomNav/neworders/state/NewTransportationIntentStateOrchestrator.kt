package com.dscorp.deliverype.presentation.ui.features.bottomNav.neworders.state

import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.ui.features.config.BaseIntentCallback

/**
 * Created by Sergio Carrillo Diestra on 15/01/2022.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 * Huacho, Peru.
 *
 **/
object NewTransportationIntentStateOrchestrator {

    private lateinit var callback: NewTransportationCallback

    fun instance(callback: NewTransportationCallback):NewTransportationIntentStateOrchestrator {
        this.callback = callback
        return this
    }

    fun orchestrate(intentState: NewTransportationIntentStates) {
        when (intentState) {
            is NewTransportationIntentStates.Error -> callback.onError(intentState.error)
            is NewTransportationIntentStates.LoadNewTranspOrdersIntent -> callback.onNewTransportationOrdersFetched(
                intentState.transportationsOrders
            )
            NewTransportationIntentStates.Loading -> callback.onLoading()
        }
    }

    interface NewTransportationCallback : BaseIntentCallback {
        fun onNewTransportationOrdersFetched(transportationsOrders: List<TransportationOrder>)
    }
}

