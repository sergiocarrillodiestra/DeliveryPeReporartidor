package com.dscorp.deliverypedrivers.data

import com.dscorp.deliverypedrivers.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TransportationOrdersService(private val api: TransportationOrderAPI) {
    fun getNewTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(api.getNewTransportationOrders(driverId)))
        }.catch {
            emit(Result.failure(it))
        }
    }

}
