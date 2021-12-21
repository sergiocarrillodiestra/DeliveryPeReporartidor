package com.dscorp.deliverypedrivers.data

import com.dscorp.deliverypedrivers.domain.TransportationOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TransportationOrdersService(private val api: TransportationOrderAPI) {
    fun getNewTransportationOrders(): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(api.getNewTransportationOrders()))
        }.catch {
            emit(Result.failure(it))
        }
    }

    fun getTakenTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(api.getTakenTransportationOrders(driverId)))
        }.catch {
            ex->emit(Result.failure(ex))
        }
    }

    fun getHistoryOfTransportationOrders(driverId: String): Flow<Result<List<TransportationOrder>>> {
        return flow {
            emit(Result.success(api.getHistoryOfTransportationOrders(driverId)))
        }.catch {
                ex->emit(Result.failure(ex))
        }
    }

}
