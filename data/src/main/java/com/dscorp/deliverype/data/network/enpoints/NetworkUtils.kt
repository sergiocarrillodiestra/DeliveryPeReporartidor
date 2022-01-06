package com.dscorp.deliverype.data.network.enpoints

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

object NetworkUtils {
    //region Invoke Retrofit functions
    /**
     * Invoke the retrofit endpoint service in IO Context and after the response has been invoked
     * verify if its successful and if has a valid body.
     */
    private suspend inline fun <T> callService(crossinline retrofitCall: suspend () -> Response<BaseResponse<T>>): Either<Failure, T> {
        return when (connectionUtils.isNetworkAvailable()) {
            true -> {
                try {
                    withContext(Dispatchers.IO) {
                        val response = retrofitCall.invoke()
                        if (response.isSuccessful && response.body() != null) {
                            return@withContext Success(response.body()!!.data)
                        } else {
                            return@withContext Error(
                                getErrorMessageFromServer(
                                    response.errorBody()?.string(), response.code()
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    return Error(parseException(e))
                }
            }
            false -> Error(Failure.NoNetworkDetected)
        }
    }

    /**
     * Use this for unit(void) responses returns.
     */
    private suspend inline fun <T> callServiceDirectly(crossinline retrofitCall: suspend () -> Response<T>): Either<Failure, T> {
        return when (connectionUtils.isNetworkAvailable()) {
            true -> {
                try {
                    withContext(Dispatchers.IO) {
                        val response = retrofitCall.invoke()
                        if (response.isSuccessful && response.body() != null) {
                            return@withContext Success(response.body()!!)
                        } else {
                            return@withContext Error(
                                getErrorMessageFromServer(
                                    response.errorBody()?.string(), response.code()
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    return Error(parseException(e))
                }
            }
            false -> Error(Failure.NoNetworkDetected)
        }
    }

    /**
     * Parse Server Error to [Failure.ServerBodyError] if [errorBody] [isServerErrorValid].
     * @param errorBody the "possible" error body that the service can return.
     * @param code the HTTP status code.
     *
     * @return [Failure] object.
     */
    private suspend fun getErrorMessageFromServer(errorBody: String?, code: Int): Failure {
        return if (errorBody != null) {
            return withContext(Dispatchers.IO) {
                val serverErrorJson = JSONObject(errorBody)
                when {
                    isServerErrorValid(serverErrorJson.toString()) -> {
                        if (code == 401 || code == 403) {
                            return@withContext Failure.UnauthorizedOrForbidden
                        } else {
                            return@withContext Failure.ServerBodyError(
                                code,
                                serverErrorJson[KEY_MESSAGE].toString()
                            )
                        }
                    }
                    (code == 401 || code == 403) -> return@withContext Failure.UnauthorizedOrForbidden
                    else -> return@withContext Failure.None
                }
            }
        } else {
            //No error body was found.
            Failure.None
        }
    }

    private fun isServerErrorValid(error: String): Boolean {
        return error.contains("\"$KEY_STATUS\"") || error.contains("\"$KEY_MESSAGE\"")
    }

    private fun parseException(throwable: Throwable): Failure {
        return when (throwable) {
            is SocketTimeoutException -> Failure.TimeOut
            is SSLException -> Failure.NetworkConnectionLostSuddenly
            is SSLHandshakeException -> Failure.SSLError
            else -> Failure.ServiceUncaughtFailure(
                throwable.message ?: "Service response doesn't match with response object."
            )
        }
    }

}