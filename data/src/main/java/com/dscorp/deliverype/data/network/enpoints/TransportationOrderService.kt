package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.domain.TransportationOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TransportationOrderService {

    @GET("getNewTransportationOrders")
    suspend fun getNewTransportationOrders(): Response<List<TransportationOrder>>

    @GET("getTakenTransportationOrdersByDriver/{id}")
    suspend fun getTakenTransportationOrders(@Path("id") driverId: String): List<TransportationOrder>

    @GET("getHistoryTransportationOrdersByDriver/{id}")
    suspend fun getHistoryOfTransportationOrders(@Path("id") driverId: String): List<TransportationOrder>
}
