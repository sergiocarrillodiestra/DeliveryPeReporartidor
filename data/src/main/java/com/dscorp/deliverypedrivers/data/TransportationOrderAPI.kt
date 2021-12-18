package com.dscorp.deliverypedrivers.data

import com.dscorp.deliverypedrivers.domain.TransportationOrder
import retrofit2.http.GET
import retrofit2.http.Path

interface TransportationOrderAPI {

    @GET("getTransportationOrdersByDriver/{id}")
    suspend fun getNewTransportationOrders(@Path("id") driverId: String): List<TransportationOrder>
}
