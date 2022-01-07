package com.dscorp.deliverype.data.network.utils

import com.google.gson.stream.MalformedJsonException
import java.net.SocketTimeoutException

/**
 * Created by Sergio Carrillo Diestra on 7/01/2022.
 * Huacho, Peru.
 * scarrillo.peruapps@gmail.com
 * For Peru Apps
 *
 **/
object MyErrorHandler {
    fun getErrorType(ex: Exception): Exception {
        return when (ex) {
            is SocketTimeoutException -> Exception("No se pudo establecer conexión con el servidor, intentelo de nuevo en unos minutos")
            is MalformedJsonException -> Exception("Json mal formado, hay un problema al en el parseo")
            else -> Exception("Error desconocido")
        }

    }
}