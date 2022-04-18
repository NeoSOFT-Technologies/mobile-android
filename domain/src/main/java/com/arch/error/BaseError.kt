package com.arch.error

import com.arch.entity.Error

sealed class BaseError @JvmOverloads constructor(
    message: String,
    val error: Error? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Exception(message, cause, enableSuppression, writableStackTrace) {

    abstract fun getFriendlyMessage(): String

    init {
        cause?.printStackTrace()
    }

    fun logError() {
        // TODO: Logging to crashlytics
    }
}
