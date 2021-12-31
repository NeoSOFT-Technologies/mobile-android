/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.error.handler

import com.arch.error.ErrorEventListener
import com.arch.error.presenters.ErrorPresenter
import com.arch.error.EventsDispatcher

interface ExceptionHandlerBinder

class ExceptionHandlerBinderImpl<T : Any>(
    errorPresenter: ErrorPresenter<T>,
    eventsDispatcher: EventsDispatcher<ErrorEventListener<T>>
) : ExceptionHandlerBinder
