package com.dscorp.deliverype.presentation.ui.features.bottomNav.takenorders.state

import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.presentation.ui.features.config.BaseIntentCallback

/**
 * Created by Sergio Carrillo Diestra on 15/01/2022.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 * Huacho, Peru.
 *
 **/
object TakenTransportationStateOrchestrator {

    private lateinit var callback: TakenTransportationCallback

    fun instance(callback: TakenTransportationCallback): TakenTransportationStateOrchestrator {
        this.callback = callback
        return this
    }

    fun orchestrate(intentState: TakenTransportationState) {
        when (intentState) {
            is TakenTransportationState.Error -> callback.onError(intentState.error)
            is TakenTransportationState.TakenTransportFetched -> callback.onTakenTransportationFetched(intentState.transportOrders)
            TakenTransportationState.Loading -> callback.onLoading()
        }
    }

    interface TakenTransportationCallback : BaseIntentCallback {
        fun onTakenTransportationFetched(transportOrders: List<TransportationOrder>){
        }
    }

}