/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.template.errors.handler

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.arch.error.ErrorEventListener
import com.arch.template.errors.presenters.ErrorPresenter


import com.arch.template.utils.EventsDispatcher


interface ExceptionHandlerBinder {
    fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity)
}

class ExceptionHandlerBinderImpl<T : Any> constructor(
    private val errorPresenter: ErrorPresenter<T>,
    private val eventsDispatcher: EventsDispatcher<ErrorEventListener<T>>
) : ExceptionHandlerBinder {
    override fun bind(lifecycleOwner: LifecycleOwner, activity: FragmentActivity) {
        eventsDispatcher.bind(lifecycleOwner, EventsListener(activity, errorPresenter))
    }

    class EventsListener<T : Any>(
        private val activity: FragmentActivity,
        private val errorPresenter: ErrorPresenter<T>
    ) : ErrorEventListener<T> {
        override fun showError(throwable: Throwable, data: T) {
            errorPresenter.show(throwable, activity, data)
        }
    }
}
