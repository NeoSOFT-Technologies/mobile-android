package com.core.error

import com.core.entity.Error

class DatabaseError(
    error: Error? = null,
    message: String,
    val dbErrorCode: Int
) : BaseError(message, error) {

    override fun getFriendlyMessage(): String {
        return "$message"
    }
}