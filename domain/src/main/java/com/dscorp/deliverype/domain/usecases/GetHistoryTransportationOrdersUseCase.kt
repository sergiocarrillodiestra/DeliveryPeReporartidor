package com.dscorp.deliverype.domain.usecases

import com.dscorp.deliverype.domain.repository.TransportationOrdersRepository

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 *
 **/
class GetHistoryTransportationOrdersUseCase(private val transportationOrdersRepository: TransportationOrdersRepository) {
    suspend operator fun invoke(driver_id:String) = transportationOrdersRepository.getHistoryOfTransportationOrders(driver_id)
}