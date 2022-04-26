package com.arch.errors.android.handler


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.arch.error.ErrorEventListener
import com.arch.error.handler.ExceptionMapper
import com.arch.error.handler.PresenterExceptionHandler
import com.arch.errors.android.presenter.IAndroidErrorPresenter
import com.arch.errors.android.utils.EventsDispatcher


class AndroidExceptionHandlerImpl<T : Any> constructor(
    private val androidErrorPresenter: IAndroidErrorPresenter<T>,
    private val eventsDispatcher: EventsDispatcher<ErrorEventListener<T>> = EventsDispatcher(),
    private val exceptionMapper: ExceptionMapper<T>,
    private val onCatch: ((Throwable) -> Unit)? = null
) : PresenterExceptionHandler<T>(
    exceptionMapper = exceptionMapper,
    errorPresenter = androidErrorPresenter,
    eventsDispatcher = eventsDispatcher,
    onCatch = onCatch
),
    IAndroidExceptionHandler {
    override fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity) {
        eventsDispatcher.bind(lifecycleOwner, EventsListener(activity, androidErrorPresenter))
    }

    class EventsListener<T : Any>(
        private val activity: FragmentActivity,
        private val androidErrorPresenter: IAndroidErrorPresenter<T>
    ) : ErrorEventListener<T> {
        override fun showError(throwable: Throwable, data: T) {
            androidErrorPresenter.show(throwable, activity, data)
        }
    }
}
