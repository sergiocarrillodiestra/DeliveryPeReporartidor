package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.domain.TransportationOrder

interface RestAPI {
    suspend fun getNewTransportationOrders(): List<TransportationOrder>
}