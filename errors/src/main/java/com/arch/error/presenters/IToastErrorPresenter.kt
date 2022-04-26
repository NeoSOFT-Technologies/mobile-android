

package com.arch.error.presenters


abstract class IToastErrorPresenter(
    duration: ToastDuration = ToastDuration.SHORT
) : IErrorPresenter<String>

enum class ToastDuration {
    SHORT,
    LONG
}
