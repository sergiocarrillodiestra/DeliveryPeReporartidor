package com.dscorp.deliverype.domain.repository

import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.entity.Either

interface TransportationOrdersRepository {

    suspend fun getNewTransportationOrders(): Either<Exception, List<TransportationOrder>>
    suspend fun getTakenTransportationOrders(driverId: String): Either<Exception, List<TransportationOrder>>
    suspend fun getHistoryOfTransportationOrders(driverId: String): Either<Exception, List<TransportationOrder>>

}