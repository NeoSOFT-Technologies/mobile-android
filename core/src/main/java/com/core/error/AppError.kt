package com.core.error

import com.core.entity.Error


class AppError(
    message: String,
    error: Error? = null,
    throwable: Throwable? = null
) : BaseError(message, error, throwable) {

    override fun getFriendlyMessage(): String {
        return message.orEmpty()
    }
}
