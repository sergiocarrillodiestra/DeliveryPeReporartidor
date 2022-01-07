package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.data.network.response.TransportationOrderResponse
import com.dscorp.deliverype.data.network.utils.NetworkUtils
import com.dscorp.deliverype.domain.entity.TransportationOrder
import com.dscorp.deliverype.domain.entity.Either

class RemoteDataSourceImpl(
    private val API: RemoteAPI,
    private val networkUtils: NetworkUtils
) : RemoteDataSource {
    override suspend fun getNewTransportationOrders(): Either<Exception, List<TransportationOrderResponse>> {
        return networkUtils.callService { API.getNewTransportationOrders() }
    }

    override suspend fun getTakenTransportationOrders(driverId: String): Either<Exception, List<TransportationOrderResponse>> {
        return networkUtils.callService { API.getTakenTransportationOrders(driverId) }
    }

    override suspend fun getHistoryOfTransportationOrders(driverId: String): Either<Exception, List<TransportationOrderResponse>> {
        return networkUtils.callService { API.getHistoryOfTransportationOrders(driverId) }
    }
}