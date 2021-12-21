package com.core.utils.network

import com.arch.utils.Either
import com.core.error.NetworkError
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * A handler for exceptions coming from the retrofit or OkHttp side.
 * Can be modified to handle error body as well in HttpException case in when(throwable) condition
 */
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Either<NetworkError, T> {
    try {
        val originalResponse = apiCall.invoke()
        val eitherResponse = originalResponse as Response<*>
        return if (!eitherResponse.isSuccessful) {
            getError(eitherResponse)
        } else {
            Either.Right(originalResponse)
        }
    } catch (throwable: Throwable) {
        return when (throwable) {
            is IOException -> Either.Left(
                NetworkError(
                    message = throwable?.localizedMessage,
                    httpError = 502
                )
            )
            is HttpException -> {
                val code = throwable.code()
                Either.Left(NetworkError(message = throwable.message(), httpError = code))
            }
            else -> {
                Either.Left(
                    NetworkError(
                        message = throwable?.localizedMessage,
                        httpError = 502
                    )
                )
            }
        }
    }
}
