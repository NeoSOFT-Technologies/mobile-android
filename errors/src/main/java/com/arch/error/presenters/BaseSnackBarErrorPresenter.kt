

package com.arch.error.presenters


open class BaseSnackBarErrorPresenter(
    duration: SnackBarDuration = SnackBarDuration.INDEFINITE
) : BaseErrorPresenter<String>

enum class SnackBarDuration {
    INDEFINITE,
    SHORT,
    LONG
}
