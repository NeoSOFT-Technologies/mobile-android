

package com.arch.error.handler


import com.arch.error.ErrorEventListener
import com.arch.error.IEventsDispatcher
import com.arch.error.presenters.IErrorPresenter


interface IExceptionHandler : IExceptionHandlerBinder {
    fun <R> handle(block: suspend () -> R): ExceptionHandlerContext<R>

    /**
     * Directly launches the error-presenter to display the error for exception [throwable].
     */
    fun showError(throwable: Throwable)

    companion object {
        operator fun <T : Any> invoke(
            exceptionMapper: ExceptionMapper<T>,
            errorPresenter: IErrorPresenter<T>,
            eventsDispatcher: IEventsDispatcher<ErrorEventListener<T>>,
            onCatch: ((Throwable) -> Unit)? = null
        ): IExceptionHandler {
            return PresenterExceptionHandler(
                exceptionMapper = exceptionMapper,
                errorPresenter = errorPresenter,
                eventsDispatcher = eventsDispatcher,
                onCatch = onCatch
            )
        }
    }
}
