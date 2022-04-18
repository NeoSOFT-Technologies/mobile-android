package com.arch.error

import com.arch.entity.Error

class NetworkError(
    error: Error? = null,
    message: String,
    val httpError: Int
) : BaseError(message, error) {

    override fun getFriendlyMessage(): String {
        return "$message"
    }
}
