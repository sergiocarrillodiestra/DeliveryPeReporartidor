package com.dscorp.deliverype.data

import com.dscorp.deliverype.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow

class TransportationOrdersRepository(private val service: TransportationOrdersService) {

    fun getNewTransportationOrders(): Flow<Result<List<TransportationOrder>>> {
        return service.getNewTransportationOrders()
    }

    fun getTakenTransportationOrders(driverId: String):Flow<Result<List<TransportationOrder>>> {
        return service.getTakenTransportationOrders(driverId)
    }

    fun getHistoryOfTransportationOrders(driverId: String):Flow<Result<List<TransportationOrder>>> {
        return service.getHistoryOfTransportationOrders(driverId)
    }


}
