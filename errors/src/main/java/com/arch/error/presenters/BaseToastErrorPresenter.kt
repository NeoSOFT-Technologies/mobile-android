

package com.arch.error.presenters


abstract class BaseToastErrorPresenter(
    duration: ToastDuration = ToastDuration.SHORT
) : BaseErrorPresenter<String>

enum class ToastDuration {
    SHORT,
    LONG
}
