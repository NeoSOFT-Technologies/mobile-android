package com.arch.template.base

import androidx.lifecycle.ViewModel
import com.arch.error.mappers.ExceptionMappersStorage
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.arch.template.errors.handler.AndroidExceptionHandlerBinder
import com.arch.template.errors.handler.AndroidExceptionHandlerBinderImpl
import com.arch.template.errors.presenters.AndroidErrorPresenter
import com.arch.template.errors.presenters.SelectorAndroidErrorPresenter
import com.arch.template.errors.presenters.SnackBarAndroidErrorPresenter
import com.arch.template.errors.presenters.ToastAndroidErrorPresenter
import com.arch.template.utils.MyAppLogger

abstract class BaseViewModel :
    ViewModel() {
    val exceptionHandler: AndroidExceptionHandlerBinder

    val snackBarErrorPresenter by lazy {
        SnackBarAndroidErrorPresenter(
            duration = SnackBarDuration.SHORT
        )
    }
    val toastErrorPresenter by lazy {
        ToastAndroidErrorPresenter(
            duration = ToastDuration.LONG
        )
    }

    init {
        exceptionHandler = AndroidExceptionHandlerBinderImpl(
            androidErrorPresenter = SelectorAndroidErrorPresenter { throwable ->
                getSelectorPresenter(throwable) ?: toastErrorPresenter
            },
            exceptionMapper = ExceptionMappersStorage.throwableMapper(),
            onCatch = {
                MyAppLogger.d("Got exception: $it")
            }
        )
    }

    open fun getSelectorPresenter(throwable: Throwable): AndroidErrorPresenter<String>? {
        return toastErrorPresenter
    }
}
