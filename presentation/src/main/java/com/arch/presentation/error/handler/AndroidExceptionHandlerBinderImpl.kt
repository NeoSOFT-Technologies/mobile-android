package com.arch.presentation.error.handler


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.arch.error.ErrorEventListener
import com.arch.error.handler.ExceptionMapper
import com.arch.error.handler.PresenterExceptionHandler
import com.arch.presentation.error.presenter.AndroidErrorPresenter
import com.arch.presentation.utils.EventsDispatcher


open class AndroidExceptionHandlerBinderImpl<T : Any> constructor(
    private val androidErrorPresenter: AndroidErrorPresenter<T>,
    private val eventsDispatcher: EventsDispatcher<ErrorEventListener<T>> = EventsDispatcher(),
    private val exceptionMapper: ExceptionMapper<T>,
    private val onCatch: ((Throwable) -> Unit)? = null
) : PresenterExceptionHandler<T>(
    exceptionMapper = exceptionMapper,
    baseErrorPresenter = androidErrorPresenter,
    errorBaseEventsDispatcher = eventsDispatcher,
    onCatch = onCatch
),
    AndroidExceptionHandlerBinder {
    override fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity) {
        eventsDispatcher.bind(lifecycleOwner, EventsListener(activity, androidErrorPresenter))
    }

    class EventsListener<T : Any>(
        private val activity: FragmentActivity,
        private val androidErrorPresenter: AndroidErrorPresenter<T>
    ) : ErrorEventListener<T> {
        override fun showError(throwable: Throwable, data: T) {
            androidErrorPresenter.show(throwable, activity, data)
        }
    }
}
