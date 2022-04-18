package com.arch.template.utils.extension

import android.content.res.Resources

fun Int.toSp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Float.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)
fun Float.toSp(): Float = (this / Resources.getSystem().displayMetrics.density)
fun Float.toDpInt(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Float.toSpInt(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.toPxInt(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)
