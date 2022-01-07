package com.dscorp.deliverype.data.network.utils

/**
 * Created by Sergio Carrillo Diestra on 7/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
sealed class ErrorHandler
{
    data class MalformedJsonException(val errorMessage:String):ErrorHandler()
    data class SocketTimeoutException(val errorMessage:String):ErrorHandler()
}
