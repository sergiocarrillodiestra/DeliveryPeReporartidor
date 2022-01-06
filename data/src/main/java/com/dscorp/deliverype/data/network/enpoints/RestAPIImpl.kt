package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.domain.TransportationOrder

class RestAPIImpl(val service:TransportationOrderService):RestAPI {
    override suspend fun getNewTransportationOrders(): List<TransportationOrder> {
        
    }
}