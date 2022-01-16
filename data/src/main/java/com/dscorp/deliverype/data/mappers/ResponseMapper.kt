package com.dscorp.deliverype.data.mappers

import com.dscorp.deliverype.data.network.response.TransportationOrderResponse
import com.dscorp.deliverype.domain.entity.TransportationOrder

/**
 * Created by Sergio Carrillo Diestra on 6/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * Peru Apps
 *
 **/
interface ResponseMapper {
    fun TransportationOrdersResponseToDomain(success: List<TransportationOrderResponse>): List<TransportationOrder>
}