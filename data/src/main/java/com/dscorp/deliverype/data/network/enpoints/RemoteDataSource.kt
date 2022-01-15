package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.data.network.response.TransportationOrderResponse
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.entity.Either
import com.dscorp.deliverype.domain.entity.Failure

interface RemoteDataSource {
    suspend fun getNewTransportationOrders(): Either<Failure, List<TransportationOrderResponse>>
    suspend fun getTakenTransportationOrders(driverId: String): Either<Failure, List<TransportationOrderResponse>>
    suspend fun getHistoryOfTransportationOrders(driverId: String): Either<Failure, List<TransportationOrderResponse>>
}