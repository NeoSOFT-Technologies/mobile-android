

package com.arch.error.handler


import com.arch.error.ErrorEventListener
import com.arch.error.IEventsDispatcher
import com.arch.error.presenters.IErrorPresenter

open class PresenterExceptionHandler<T : Any>(
    private val exceptionMapper: ExceptionMapper<T>,
    private val errorPresenter: IErrorPresenter<T>,
    private val eventsDispatcher: IEventsDispatcher<ErrorEventListener<T>>,
    private val onCatch: ((Throwable) -> Unit)? = null
) : IExceptionHandlerBinder by ExceptionHandlerBinderImpl<T>(
    errorPresenter,
    eventsDispatcher
), IExceptionHandler {
    override fun <R> handle(block: suspend () -> R): ExceptionHandlerContext<R> {
        return ExceptionHandlerContext(exceptionMapper, eventsDispatcher, onCatch, block)
    }

    override fun showError(throwable: Throwable) {
        val errorValue = exceptionMapper(throwable)
        eventsDispatcher.dispatchEvent { showError(throwable, errorValue) }
    }
}
