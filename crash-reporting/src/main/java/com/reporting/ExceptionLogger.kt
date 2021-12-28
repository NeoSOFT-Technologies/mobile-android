package com.reporting

internal interface ExceptionLogger {
    fun setUserId(userId: String)
    fun setCustomValue(value: String, forKey: String)
    fun recordException(throwable: Throwable)
    fun log(message: String)
}