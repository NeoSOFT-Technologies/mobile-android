package com.core.error

import android.util.Log
import com.core.entity.Error

sealed class BaseError @JvmOverloads constructor(
    message: String,
    val error: Error? = null,
    cause: Throwable? = null,
    enableSuppression: Boolean = false,
    writableStackTrace: Boolean = false
) : Exception(message, cause, enableSuppression, writableStackTrace) {

    abstract fun getFriendlyMessage(): String

    init {
        Log.d("ERROR", "Error: $message")
        cause?.printStackTrace()
    }

    fun logError() {
        // TODO: Logging to crashlytics
    }
}
