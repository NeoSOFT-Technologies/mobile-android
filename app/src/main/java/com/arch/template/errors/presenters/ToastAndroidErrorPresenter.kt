

package com.arch.template.errors.presenters

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.BaseToastErrorPresenter
import com.arch.error.presenters.ToastDuration
import com.arch.template.utils.MyAppLogger


class ToastAndroidErrorPresenter constructor(
    private val duration: ToastDuration
) : BaseToastErrorPresenter(duration = duration), AndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        MyAppLogger.d("ToastErrorPresenter show")
        Toast.makeText(
            activity,
            data,
            duration.toAndroidCode()
        ).show()
    }
}
