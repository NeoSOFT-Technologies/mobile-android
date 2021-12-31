

package com.arch.error

sealed class HandlerResult<out T : Any?, out E : Throwable> {
    class Success<out T : Any?>(val value: T) : HandlerResult<T, Nothing>()
    class Failure<out E : Throwable>(val error: E) : HandlerResult<Nothing, E>()
}
