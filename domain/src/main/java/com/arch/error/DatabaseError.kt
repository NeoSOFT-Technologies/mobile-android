package com.arch.error

import com.arch.entity.Error

class DatabaseError(
    error: Error? = null,
    message: String,
    val dbErrorCode: Int
) : BaseError(message, error) {

    override fun getFriendlyMessage(): String {
        return "$message"
    }
}