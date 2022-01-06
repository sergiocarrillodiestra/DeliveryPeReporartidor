package com.dscorp.deliverype.data

import com.dscorp.deliverype.data.network.enpoints.TransportationOrderService
import com.dscorp.deliverype.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TransportationOrdersService(private val service: TransportationOrderService) {
    suspend fun getNewTransportationOrders(): List<TransportationOrder> {
        val response = service.getNewTransportationOrders()

        return if (response.isSuccessful && response.code() == 200) {
            response.body().orEmpty()
        } else {
            emptyList()
        }
    }

    fun getTakenTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(service.getTakenTransportationOrders(driverId)))
        }.catch { ex ->
            emit(Result.failure(ex))
        }
    }

    fun getHistoryOfTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(service.getHistoryOfTransportationOrders(driverId)))
        }.catch { ex ->
            emit(Result.failure(ex))
        }
    }

}
