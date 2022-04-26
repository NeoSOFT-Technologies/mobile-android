package com.arch.errors.android.presenter

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.IAlertErrorPresenter
import com.arch.errors.android.AlertDialogFragment
import com.arch.errors.android.R


class AlertAndroidErrorPresenter(
    private val title: String = "",
    private val positiveButtonText: String = ""
) :
    IAlertErrorPresenter(),
    IAndroidErrorPresenter<String> {

    override fun show(throwable: Throwable, activity: FragmentActivity, data: String) {
        AlertDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(
                    AlertDialogFragment.ARGS_KEY,
                    AlertDialogFragment.DialogSettings(
                        title = if (title.isEmpty()) activity.getString(R.string.alert_error_title) else title,
                        positiveButtonText = if (positiveButtonText.isEmpty()) activity.getString(
                            R.string.alert_error_positive
                        ) else positiveButtonText,
                        messageText = data,
                    )
                )
            }
        }.show(activity.supportFragmentManager, AlertDialogFragment.ALERT_DIALOG_FRAGMENT_TAG)
    }
}
