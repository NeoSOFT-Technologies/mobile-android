package com.arch.errors.android

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.parcelize.Parcelize

class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val settings: DialogSettings = arguments?.getParcelable(ARGS_KEY)
            ?: DialogSettings(
                title = getString(R.string.alert_error_title),
                positiveButtonText = getString(R.string.alert_error_positive),
                messageText = getString(R.string.alert_error_message)
            )

        return activity?.let {
            AlertDialog.Builder(it)
                .setTitle(settings.title)
                .setMessage(settings.messageText)
                .setPositiveButton(settings.positiveButtonText) { _, _ -> }
                .create()
        } ?: throw IllegalStateException("Activity can't be null.")
    }

    @Parcelize
    data class DialogSettings(
        val title: String,
        val positiveButtonText: String,
        val messageText: String
    ) : Parcelable

    companion object {
        const val ALERT_DIALOG_FRAGMENT_TAG = "alert_dialog_fragment"
        internal const val ARGS_KEY = "dialog_settings"
    }
}