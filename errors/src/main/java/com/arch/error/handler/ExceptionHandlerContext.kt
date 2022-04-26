

package com.arch.error.handler


import com.arch.error.ErrorEventListener
import com.arch.error.HandlerResult
import com.arch.error.IEventsDispatcher

abstract class ExceptionHandlerContext<R> {
    abstract suspend fun execute(): HandlerResult<R, Throwable>
    abstract fun <E : Throwable> catch(
        condition: (Throwable) -> Boolean,
        catcher: (E) -> Boolean
    ): ExceptionHandlerContext<R>
    abstract fun finally(block: () -> Unit): ExceptionHandlerContext<R>

    inline fun <reified E : Throwable> catch(
        noinline catcher: (E) -> Boolean
    ): ExceptionHandlerContext<R> {
        return catch(
            condition = { it is E },
            catcher = catcher
        )
    }

    companion object {
        operator fun <T : Any, R> invoke(
            exceptionMapper: ExceptionMapper<T>,
            baseEventsDispatcher: IEventsDispatcher<ErrorEventListener<T>>,
            onCatch: ((Throwable) -> Unit)?,
            block: suspend () -> R
        ): ExceptionHandlerContext<R> {
            return ExceptionHandlerContextImpl(
                exceptionMapper = exceptionMapper,
                baseEventsDispatcher = baseEventsDispatcher,
                onCatch = onCatch,
                block = block
            )
        }
    }
}
