package com.dscorp.deliverype.data.network.utils

import com.dscorp.deliverype.data.network.response.BaseResponse
import com.dscorp.deliverype.domain.entity.Either
import com.dscorp.deliverype.domain.entity.Either.Error
import com.dscorp.deliverype.domain.entity.Either.Success
import com.google.gson.stream.MalformedJsonException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException
class NetworkUtils(val connectionUtils: ConnectionUtils) {
    /**
     * Invoke the retrofit endpoint service in IO Context and after the response has been invoked
     * verify if its successful and if has a valid body.
     */
    suspend inline fun <T> callService(crossinline retrofitCall: suspend () -> Response<BaseResponse<T>>): Either<Exception, T> {
        return when (connectionUtils.isNetworkAvailable()) {
            true -> {
                try {
                    withContext(Dispatchers.IO) {
                        val response = retrofitCall.invoke()
                        if (response.isSuccessful && response.body() != null) {
                            return@withContext Success(response.body()!!.data)
                        } else {
                            val ex = Exception("${response.errorBody()?.string()}")
                            ex.printStackTrace()
                            return@withContext Error(
                                getErrorMessageFromServer(
                                    response.errorBody()?.string(), response.code()
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    return when (e) {
                        is SocketTimeoutException -> Error(Exception("No se pudo establecer conexión con el servidor, intentelo de nuevo en unos minutos"))
                        is MalformedJsonException -> Error(Exception("Json mal formado, hay un problema al en el parseo"))
                        else -> Error(e)
                    }


                }
            }
            false -> {
                val ex = Exception("No hay conexión a internet")
                ex.printStackTrace()
                Error(ex)
            }
        }


    }

    fun getErrorMessageFromServer(errorBody: String?, code: Int): Exception {
        return if (errorBody != null) {
            val serverErrorJson = JSONObject(errorBody)
            when {
                isServerErrorValid(serverErrorJson.toString()) -> {
                    return if (code == 401 || code == 403) {
                        Exception("Unauthorized Or Forbidden")
                    } else {
                        Exception(serverErrorJson[KEY_MESSAGE].toString())
                    }
                }
                else -> Exception("None")
            }
        } else
            return Exception("None")
    }

    private fun isServerErrorValid(error: String): Boolean {
        return error.contains("\"$KEY_STATUS\"") || error.contains("\"$KEY_MESSAGE\"")
    }

    companion object {
        private const val KEY_STATUS = "status"
        private const val KEY_MESSAGE = "message"
    }
}