package com.arch.error

import com.arch.entity.Error


class AppError(
    message: String? = "",
    error: Error? = null,
    throwable: Throwable? = null,
    val appErrorType: AppErrorType? = AppErrorType.None,
) : BaseError(message ?: "", error, throwable) {

    override fun getFriendlyMessage(): String {
        return message.orEmpty()
    }
}
