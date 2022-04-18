package com.arch.data.network.utils


import com.arch.error.NetworkError
import com.arch.utils.Either
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
        return when {
            !eitherResponse.isSuccessful -> {
                getError(eitherResponse)
            }
            eitherResponse.body()==null -> {
                getError(eitherResponse)
            }
            else -> {
                Either.Right(originalResponse)
            }
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
