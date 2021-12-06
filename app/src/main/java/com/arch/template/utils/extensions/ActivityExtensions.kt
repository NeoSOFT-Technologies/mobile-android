package com.arch.template.utils.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.arch.template.R
import com.arch.template.ui.molecule.AppToast

fun Activity.showShortToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, duration).show()

fun Activity.showLongToast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) =
    Toast.makeText(this, message, duration).show()

fun Activity.showErrorToast(message: CharSequence, duration: Int) {
    val toast = AppToast(
        context = this,
        message = message.toString(),
        textSize = resources.getDimension(R.dimen.helper_text_size).toSp(),
        textColor = Color.WHITE,
        backgroundColor = resources.getColor(R.color.secondaryRedAlert),
        duration = duration
    )
    toast.show()
}

fun Activity.showSuccessToast(message: CharSequence, duration: Int) {
    val toast = AppToast(
        context = this,
        message = message.toString(),
        textSize = resources.getDimension(R.dimen.helper_text_size).toSp(),
        textColor = Color.WHITE,
        backgroundColor = resources.getColor(R.color.colorToastSuccess),
        duration = duration
    )
    toast.show()
}

inline fun Activity.alertDialog(body: AlertDialog.Builder.() -> AlertDialog.Builder): AlertDialog {
    return AlertDialog.Builder(this)
        .body()
        .show()
}

fun Toast.createToast(
    context: Context?,
    message: String?,
    gravity: Int,
    duration: Int,
    backgroundColor: Int,
    imagebackground: Int
) {
    val inflater: LayoutInflater =
        context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(
        R.layout.custom_toast,
        (context as Activity).findViewById(R.id.cv_toast_layout)
    )

    layout.findViewById<CardView>(R.id.cv_toast_layout).radius = 16.0F
    layout.findViewById<CardView>(R.id.cv_toast_layout).setCardBackgroundColor(backgroundColor)
    layout.findViewById<TextView>(R.id.app_toast_message).text = message
    layout.findViewById<ImageView>(R.id.iv_toast_image).setImageResource(imagebackground)
    setGravity(gravity, 0, 25)
    setDuration(duration)

    view = layout
    show()
}
