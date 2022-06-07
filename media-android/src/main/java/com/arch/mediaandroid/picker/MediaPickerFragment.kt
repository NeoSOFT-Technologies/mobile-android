package com.arch.mediaandroid.picker

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.arch.mediaandroid.MediaFactory
import com.arch.medialibrary.Media
import com.arch.medialibrary.picker.CanceledException


class MediaPickerFragment : Fragment() {

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()

    init {
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    fun pickVideo(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        val intent = Intent().apply {
            type = "video/*"
            action = Intent.ACTION_GET_CONTENT
        }

        startActivityForResult(intent, requestCode)
    }

    fun pickMedia(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        val intent = Intent().apply {
            type = "image/* video/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*", "image/*"))
        }
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val callbackData = codeCallbackMap[requestCode] ?: return
        codeCallbackMap.remove(requestCode)

        val callback = callbackData.callback

        if (resultCode == Activity.RESULT_CANCELED) {
            callback.invoke(Result.failure(CanceledException()))
            return
        }

        processResult(callback, data)
    }

    @Suppress("ReturnCount")
    private fun processResult(
        callback: (Result<Media>) -> Unit,
        intent: Intent?
    ) {
        val context = this.context
        if (context == null) {
            callback(Result.failure(IllegalStateException("context unavailable")))
            return
        }
        if (intent == null) {
            callback(Result.failure(IllegalStateException("intent unavailable")))
            return
        }
        val intentData = intent.data
        if (intentData == null) {
            callback(Result.failure(IllegalStateException("intentData unavailable")))
            return
        }

        val result = kotlin.runCatching {
            MediaFactory.create(context, intentData)
        }
        callback.invoke(result)
    }

    class CallbackData(val callback: (Result<Media>) -> Unit)
}
