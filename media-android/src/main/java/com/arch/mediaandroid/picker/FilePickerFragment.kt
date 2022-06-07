package com.arch.mediaandroid.picker

import android.app.Activity
import android.content.Intent
import android.os.Environment
import androidx.fragment.app.Fragment
import com.arch.medialibrary.FileMedia
import com.arch.medialibrary.picker.CanceledException
import com.nbsp.materialfilepicker.MaterialFilePicker
import com.nbsp.materialfilepicker.ui.FilePickerActivity

import java.io.File



class FilePickerFragment : Fragment() {
    init {
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()

    fun pickFile(callback: (Result<FileMedia>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)


        val externalStorage = Environment.getExternalStorageDirectory()
        MaterialFilePicker().withSupportFragment(this)
            .withCloseMenu(true)
            .withRootPath(externalStorage.absolutePath)
            .withRequestCode(requestCode)
            .start()

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

    private fun processResult(
        callback: (Result<FileMedia>) -> Unit,
        data: Intent?
    ) {
        val filePath = data?.getStringExtra(FilePickerActivity.RESULT_FILE_PATH)

        filePath?.let { path ->
            val name = File(path).name
            callback(Result.success(FileMedia(name, path)))
        }
    }

    class CallbackData(val callback: (Result<FileMedia>) -> Unit)
}