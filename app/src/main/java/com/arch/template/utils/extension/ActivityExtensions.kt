package com.arch.template.utils.extension

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Activity.showShortToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(
        this,
        message.toString(),
        duration
    ).show()

fun Activity.showLongToast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    Toast(this).apply {
        this.duration = duration
        this.setText(message.toString())

    }.show()


inline fun Activity.alertDialog(body: AlertDialog.Builder.() -> AlertDialog.Builder): AlertDialog {
    return AlertDialog.Builder(this)
        .body()
        .show()
}
