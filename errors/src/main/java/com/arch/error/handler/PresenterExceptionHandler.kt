/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error.handler

import com.arch.error.ErrorEventListener
import com.arch.error.presenters.ErrorPresenter
import com.arch.error.EventsDispatcher

internal class PresenterExceptionHandler<T : Any>(
    private val exceptionMapper: ExceptionMapper<T>,
    private val errorPresenter: ErrorPresenter<T>,
    private val errorEventsDispatcher: EventsDispatcher<ErrorEventListener<T>>,
    private val onCatch: ((Throwable) -> Unit)? = null
) : ExceptionHandlerBinder by ExceptionHandlerBinderImpl<T>(
    errorPresenter,
    errorEventsDispatcher
), ExceptionHandler {
    override fun <R> handle(block: suspend () -> R): ExceptionHandlerContext<R> {
        return ExceptionHandlerContext(exceptionMapper, errorEventsDispatcher, onCatch, block)
    }

    override fun showError(throwable: Throwable) {
        val errorValue = exceptionMapper(throwable)
        errorEventsDispatcher.dispatchEvent { showError(throwable, errorValue) }
    }
}
