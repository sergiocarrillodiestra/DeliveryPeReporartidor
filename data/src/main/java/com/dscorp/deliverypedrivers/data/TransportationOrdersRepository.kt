package com.dscorp.deliverypedrivers.data

import com.dscorp.deliverypedrivers.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow

class TransportationOrdersRepository(private val service: TransportationOrdersService) {
    fun getNewTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return service.getNewTransportationOrders(driverId)
    }


}
