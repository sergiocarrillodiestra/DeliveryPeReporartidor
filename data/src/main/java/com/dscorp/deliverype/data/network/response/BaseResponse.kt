package com.dscorp.deliverype.data.network.response

data class BaseResponse<R>(
    val code: Int,
    val data: R,
    val message: String
)