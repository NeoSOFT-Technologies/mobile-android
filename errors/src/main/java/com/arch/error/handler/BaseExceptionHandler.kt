

package com.arch.error.handler

import com.arch.error.BaseEventsDispatcher
import com.arch.error.ErrorEventListener
import com.arch.error.presenters.BaseErrorPresenter


interface BaseExceptionHandler : BaseExceptionHandlerBinder {
    fun <R> handle(block: suspend () -> R): ExceptionHandlerContext<R>

    /**
     * Directly launches the error-presenter to display the error for exception [throwable].
     */
    fun showError(throwable: Throwable)

    companion object {
        operator fun <T : Any> invoke(
            exceptionMapper: ExceptionMapper<T>,
            baseErrorPresenter: BaseErrorPresenter<T>,
            errorBaseEventsDispatcher: BaseEventsDispatcher<ErrorEventListener<T>>,
            onCatch: ((Throwable) -> Unit)? = null
        ): BaseExceptionHandler {
            return PresenterExceptionHandler(
                exceptionMapper = exceptionMapper,
                baseErrorPresenter = baseErrorPresenter,
                errorBaseEventsDispatcher = errorBaseEventsDispatcher,
                onCatch = onCatch
            )
        }
    }
}
