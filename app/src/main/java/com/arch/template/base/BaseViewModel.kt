package com.arch.template.base

import androidx.lifecycle.ViewModel
import com.arch.error.mappers.ExceptionMappersStorage
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.arch.template.errors.handler.AndroidExceptionHandlerBinder
import com.arch.template.errors.handler.AndroidExceptionHandlerBinderImpl
import com.arch.template.errors.presenters.*
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
    val alertErrorPresenter by lazy {
        AlertAndroidErrorPresenter()
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
