package com.core.utils

import com.core.error.AppError

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val error: AppError?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> error(msg: String, data: T? = null, appError: AppError? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                msg,
                appError
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                null
            )
        }
    }
}
