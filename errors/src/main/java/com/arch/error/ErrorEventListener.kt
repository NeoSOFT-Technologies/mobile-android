

package com.arch.error

interface ErrorEventListener<T> {
    fun showError(throwable: Throwable, data: T)
}
