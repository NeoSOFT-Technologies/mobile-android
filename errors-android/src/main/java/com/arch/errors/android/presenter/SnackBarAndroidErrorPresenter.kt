package com.arch.errors.android.presenter

import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.arch.error.presenters.ISnackBarErrorPresenter
import com.arch.error.presenters.SnackBarDuration
import com.arch.errors.android.toAndroidCode
import com.google.android.material.snackbar.Snackbar


class SnackBarAndroidErrorPresenter constructor(
    private val duration: SnackBarDuration
) : ISnackBarErrorPresenter(duration = duration), IAndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        val decorView: View = if (activity.hasWindowFocus()) {
            activity.window?.decorView
        } else {
            val dialogFragment = activity.supportFragmentManager.findActiveDialogFragment()
            dialogFragment?.dialog?.window?.decorView
        } ?: return
        val contentView: View = decorView.findViewById(android.R.id.content) ?: return
        val snackbar = Snackbar.make(
            contentView,
            data,//data.toString(activity),
            duration.toAndroidCode()
        )
        snackbar.show()
    }

    private fun FragmentManager.findActiveDialogFragment(): DialogFragment? {
        val dialogFragment: DialogFragment? = fragments.filterIsInstance<DialogFragment>()
            .filter { it.showsDialog }
            .filter { it.isResumed }
            .filter { it.dialog?.window?.decorView?.hasWindowFocus() == true }
            .firstOrNull()

        if (dialogFragment != null) return dialogFragment

        return fragments.firstNotNullOfOrNull { it.childFragmentManager.findActiveDialogFragment() }
    }
}
