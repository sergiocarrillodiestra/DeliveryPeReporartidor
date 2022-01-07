package com.dscorp.deliverype.data.network.enpoints

import com.dscorp.deliverype.data.network.response.BaseResponse
import com.dscorp.deliverype.data.network.response.TransportationOrderResponse
import com.dscorp.deliverype.domain.entity.TransportationOrder
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteAPI {

    @GET("getNewTransportationOrders")
    suspend fun getNewTransportationOrders(): Response<BaseResponse<List<TransportationOrderResponse>>>

    @GET("getTakenTransportationOrdersByDriver/{id}")
    suspend fun getTakenTransportationOrders(@Path("id") driverId: String): Response<BaseResponse<List<TransportationOrderResponse>>>

    @GET("getHistoryTransportationOrdersByDriver/{id}")
    suspend fun getHistoryOfTransportationOrders(@Path("id") driverId: String): Response<BaseResponse<List<TransportationOrderResponse>>>
}
