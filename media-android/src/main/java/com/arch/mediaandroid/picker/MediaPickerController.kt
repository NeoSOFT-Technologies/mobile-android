package com.arch.mediaandroid.picker

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.arch.mediaandroid.AndroidBitmap
import com.arch.medialibrary.FileMedia
import com.arch.medialibrary.Media
import com.arch.medialibrary.picker.MediaPickerController
import com.arch.medialibrary.picker.MediaSource
import com.arch.permissions.android.IAndroidPermissionsController


interface AndroidMediaPickerController : MediaPickerController {
    override val permissionsController: IAndroidPermissionsController
    override suspend fun pickImage(source: MediaSource): AndroidBitmap
    override suspend fun pickImage(
        source: MediaSource,
        maxWidth: Int,
        maxHeight: Int
    ): AndroidBitmap

    override suspend fun pickMedia(): Media
    override suspend fun pickFiles(): FileMedia

    fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager)

    companion object {
        operator fun invoke(
            permissionsController: IAndroidPermissionsController,
            pickerFragmentTag: String = "MediaControllerPicker",
            filePickerFragmentTag: String = "FileMediaControllerPicker"
        ): AndroidMediaPickerController {
            return MediaPickerControllerImpl(
                permissionsController = permissionsController,
                pickerFragmentTag = pickerFragmentTag,
                filePickerFragmentTag = filePickerFragmentTag
            )
        }
    }
}