

package com.arch.error.handler

import com.arch.error.BaseEventsDispatcher
import com.arch.error.ErrorEventListener
import com.arch.error.presenters.BaseErrorPresenter

open class PresenterExceptionHandler<T : Any>(
    private val exceptionMapper: ExceptionMapper<T>,
    private val baseErrorPresenter: BaseErrorPresenter<T>,
    private val errorBaseEventsDispatcher: BaseEventsDispatcher<ErrorEventListener<T>>,
    private val onCatch: ((Throwable) -> Unit)? = null
) : BaseExceptionHandlerBinder by BaseExceptionHandlerBinderImpl<T>(
    baseErrorPresenter,
    errorBaseEventsDispatcher
), BaseExceptionHandler {
    override fun <R> handle(block: suspend () -> R): ExceptionHandlerContext<R> {
        return ExceptionHandlerContext(exceptionMapper, errorBaseEventsDispatcher, onCatch, block)
    }

    override fun showError(throwable: Throwable) {
        val errorValue = exceptionMapper(throwable)
        errorBaseEventsDispatcher.dispatchEvent { showError(throwable, errorValue) }
    }
}
