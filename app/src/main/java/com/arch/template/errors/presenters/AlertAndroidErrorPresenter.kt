package com.arch.template.errors.presenters

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.arch.error.presenters.BaseAlertErrorPresenter
import com.arch.presentation.error.presenter.AndroidErrorPresenter
import com.arch.template.R

class AlertAndroidErrorPresenter(
    private val title: String = "",
    private val positiveButtonText: String = ""
) :
    BaseAlertErrorPresenter(),
    AndroidErrorPresenter<String> {

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
