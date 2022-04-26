

package com.arch.error.handler

import com.arch.error.IEventsDispatcher
import com.arch.error.ErrorEventListener
import com.arch.error.presenters.IErrorPresenter

interface IExceptionHandlerBinder

class ExceptionHandlerBinderImpl<T : Any>(
    IErrorPresenter: IErrorPresenter<T>,
    iEventsDispatcher: IEventsDispatcher<ErrorEventListener<T>>
) : IExceptionHandlerBinder
