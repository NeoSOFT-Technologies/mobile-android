package com.arch.data.network.utils


import com.arch.data.entity.remote.error.ErrorResponseEntity
import com.arch.data.utils.NonNullUtils
import com.arch.error.NetworkError
import com.arch.utils.Either
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Response

/**
 * A function to retrieve error messages from error body if it exists, else fallback to the generic error message from Response class
 */

fun <T> getError(apiResponse: Response<out Any>): Either<NetworkError, T> {
    return apiResponse.errorBody()?.let {
        try {
            val moshi = Moshi.Builder().build()
            val jsonAdapter: JsonAdapter<ErrorResponseEntity?> =
                moshi.adapter(ErrorResponseEntity::class.java)
            val errorResponseEntity: ErrorResponseEntity? = jsonAdapter.fromJson(it.string())
            errorResponseEntity?.let {
                Either.Left(
                    NetworkError(
                        message = NonNullUtils.getNonNull(
                            errorResponseEntity.error
                        ),
                        httpError = apiResponse.code()
                    )
                )
            } ?: run {
                Either.Left(
                    NetworkError(
                        message = apiResponse.message(),
                        httpError = apiResponse.code()
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Either.Left(
                NetworkError(
                    message = apiResponse.message(),
                    httpError = apiResponse.code()
                )
            )
        }
    } ?: run {
        Either.Left(
            NetworkError(
                message = apiResponse.message(),
                httpError = apiResponse.code()
            )
        )
    }
}
