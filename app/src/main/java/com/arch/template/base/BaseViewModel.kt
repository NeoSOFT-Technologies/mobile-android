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
import com.core.utils.ResourceString
import com.core.utils.SingleLiveEvent
import com.core.utils.TextResourceString

/***
 * @ shouldOverrideErrorPresenter true when based on the exception need to pass different
 * errorPresenter
 * please override getSelectorPresenter method and return error presenter based on the exception
 */
abstract class BaseViewModel(private val shouldOverrideErrorPresenter: Boolean = false) :
    ViewModel() {
    val exceptionHandler: AndroidExceptionHandlerBinder

    internal val snackBarErrorPresenter = SnackBarAndroidErrorPresenter(
        duration = SnackBarDuration.SHORT
    )
    internal val toastErrorPresenter = ToastAndroidErrorPresenter(
        duration = ToastDuration.LONG
    )

    init {
        exceptionHandler = AndroidExceptionHandlerBinderImpl(
            androidErrorPresenter = SelectorAndroidErrorPresenter { throwable ->
                when {
                    shouldOverrideErrorPresenter -> {
                        getSelectorPresenter(throwable)
                    }
                    else -> {
                        snackBarErrorPresenter
                    }
                }
            },
            exceptionMapper = ExceptionMappersStorage.throwableMapper(),
            onCatch = {
                MyAppLogger.d("Got exception: $it")
            }
        )
    }

    open fun getSelectorPresenter(throwable: Throwable): AndroidErrorPresenter<String> {
        return toastErrorPresenter
    }

    internal val toast = SingleLiveEvent<ResourceString>()

    fun showToastWithString(str: String) {
        toast.value = TextResourceString(str)
    }

    fun showToastWithStringAsync(str: String) {
        toast.postValue(TextResourceString(str))
    }
}
