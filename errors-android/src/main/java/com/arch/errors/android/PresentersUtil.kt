

package com.arch.errors.android

import android.widget.Toast
import com.arch.error.presenters.SnackBarDuration
import com.arch.error.presenters.ToastDuration
import com.google.android.material.snackbar.Snackbar

fun SnackBarDuration.toAndroidCode(): Int = when (this) {
    SnackBarDuration.INDEFINITE -> Snackbar.LENGTH_INDEFINITE
    SnackBarDuration.SHORT -> Snackbar.LENGTH_SHORT
    SnackBarDuration.LONG -> Snackbar.LENGTH_LONG
}

fun ToastDuration.toAndroidCode(): Int = when (this) {
    ToastDuration.SHORT -> Toast.LENGTH_SHORT
    ToastDuration.LONG -> Toast.LENGTH_LONG
}
