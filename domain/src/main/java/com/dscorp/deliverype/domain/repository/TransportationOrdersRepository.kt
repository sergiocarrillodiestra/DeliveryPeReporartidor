package com.dscorp.deliverype.domain.repository

import com.dscorp.deliverype.domain.entity.Either
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder

interface TransportationOrdersRepository {

    suspend fun getNewTransportationOrders(): Either<Failure, List<TransportationOrder>>
    suspend fun getTakenTransportationOrders(driverId: String): Either<Failure, List<TransportationOrder>>
    suspend fun getHistoryOfTransportationOrders(driverId: String): Either<Failure, List<TransportationOrder>>

}