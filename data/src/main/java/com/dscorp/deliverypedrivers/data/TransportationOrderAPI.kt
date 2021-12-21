package com.dscorp.deliverypedrivers.data

import com.dscorp.deliverypedrivers.domain.TransportationOrder
import retrofit2.http.GET
import retrofit2.http.Path

interface TransportationOrderAPI {

    @GET("getNewTransportationOrders")
    suspend fun getNewTransportationOrders(): List<TransportationOrder>

    @GET("getTakenTransportationOrdersByDriver/{id}")
    suspend fun getTakenTransportationOrders(@Path("id") driverId: String): List<TransportationOrder>

    @GET("getHistoryTransportationOrdersByDriver/{id}")
    suspend fun getHistoryOfTransportationOrders(@Path("id") driverId: String): List<TransportationOrder>
}
