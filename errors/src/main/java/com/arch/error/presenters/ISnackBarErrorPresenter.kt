

package com.arch.error.presenters


open class ISnackBarErrorPresenter(
    duration: SnackBarDuration = SnackBarDuration.INDEFINITE
) : IErrorPresenter<String>

enum class SnackBarDuration {
    INDEFINITE,
    SHORT,
    LONG
}
