/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package com.arch.template.errors.presenters

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.ToastDuration


class ToastErrorPresenter constructor(
    private val duration: ToastDuration
) : ErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        Toast.makeText(
            activity,
            data,
            duration.toAndroidCode()
        ).show()
    }
}
