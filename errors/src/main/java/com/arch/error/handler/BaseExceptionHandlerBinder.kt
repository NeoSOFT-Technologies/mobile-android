

package com.arch.error.handler

import com.arch.error.BaseEventsDispatcher
import com.arch.error.ErrorEventListener
import com.arch.error.presenters.BaseErrorPresenter

interface BaseExceptionHandlerBinder

class BaseExceptionHandlerBinderImpl<T : Any>(
    baseErrorPresenter: BaseErrorPresenter<T>,
    baseEventsDispatcher: BaseEventsDispatcher<ErrorEventListener<T>>
) : BaseExceptionHandlerBinder
