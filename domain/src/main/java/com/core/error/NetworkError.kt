package com.core.error

import com.core.entity.Error

class NetworkError(
    error: Error? = null,
    message: String,
    val httpError: Int
) : BaseError(message, error) {

    override fun getFriendlyMessage(): String {
        return "$message"
    }
}
