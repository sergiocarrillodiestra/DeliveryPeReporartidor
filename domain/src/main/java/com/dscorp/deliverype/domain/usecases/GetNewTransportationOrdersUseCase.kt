package com.dscorp.deliverype.domain.usecases

import com.dscorp.deliverype.domain.entity.Either
import com.dscorp.deliverype.domain.entity.Failure
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.repository.TransportationOrdersRepository

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 *
 **/
class GetNewTransportationOrdersUseCase(private val transportationOrdersRepository: TransportationOrdersRepository) {
    suspend operator fun invoke():Either<Failure,List<TransportationOrder>> = transportationOrdersRepository.getNewTransportationOrders()
}