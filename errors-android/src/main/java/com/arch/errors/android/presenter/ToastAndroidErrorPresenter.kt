

package com.arch.errors.android.presenter

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.IToastErrorPresenter
import com.arch.error.presenters.ToastDuration
import com.arch.errors.android.toAndroidCode


class ToastAndroidErrorPresenter constructor(
    private val duration: ToastDuration
) : IToastErrorPresenter(duration = duration), IAndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        Toast.makeText(
            activity,
            data,
            duration.toAndroidCode()
        ).show()
    }
}
