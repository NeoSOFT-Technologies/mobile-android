package com.arch.utils

import com.arch.error.BaseError

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val error: BaseError?
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

        fun <T> error(msg: String, data: T? = null, appError: BaseError? = null): Resource<T> {
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
