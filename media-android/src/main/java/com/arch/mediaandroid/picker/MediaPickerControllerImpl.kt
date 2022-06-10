package com.arch.mediaandroid.picker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.arch.mediaandroid.AndroidBitmap
import com.arch.medialibrary.FileMedia
import com.arch.medialibrary.Media
import com.arch.medialibrary.picker.DEFAULT_MAX_IMAGE_HEIGHT
import com.arch.medialibrary.picker.DEFAULT_MAX_IMAGE_WIDTH
import com.arch.medialibrary.picker.MediaPickerController
import com.arch.medialibrary.picker.MediaSource
import com.arch.permissions.IPermissionsController
import com.arch.permissions.Permission
import com.arch.permissions.android.IAndroidPermissionsController

import kotlin.coroutines.suspendCoroutine

internal class MediaPickerControllerImpl(
    override val permissionsController: IAndroidPermissionsController,
    private val pickerFragmentTag: String,
    private val filePickerFragmentTag: String
) : AndroidMediaPickerController {
    var fragmentManager: FragmentManager? = null

    override fun bind(lifecycle: Lifecycle, fragmentManager: FragmentManager) {
        permissionsController.bind(lifecycle, fragmentManager)

        this.fragmentManager = fragmentManager

        val observer = object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroyed(source: LifecycleOwner) {
                this@MediaPickerControllerImpl.fragmentManager = null
                source.lifecycle.removeObserver(this)
            }
        }
        lifecycle.addObserver(observer)
    }

    override suspend fun pickImage(source: MediaSource): AndroidBitmap {
        return pickImage(source, DEFAULT_MAX_IMAGE_WIDTH, DEFAULT_MAX_IMAGE_HEIGHT)
    }

    /**
     * A default values for [maxWidth] and [maxHeight] arguments are not used because bug of kotlin
     * compiler. Default values for suspend functions don't work correctly.
     * (Look here: https://youtrack.jetbrains.com/issue/KT-37331)
     */
    override suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AndroidBitmap {
        val fragmentManager =
            fragmentManager ?: throw IllegalStateException("can't pick image without active window")

        source.requiredPermissions().forEach { permission ->
            permissionsController.requestPermission(permission)
        }

        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(pickerFragmentTag)
        val imagePickerFragment: ImagePickerFragment = if (currentFragment != null && currentFragment is ImagePickerFragment) {
            currentFragment
        } else {
            ImagePickerFragment.newInstance(maxWidth, maxHeight).also {
                fragmentManager
                    .beginTransaction()
                    .add(it, pickerFragmentTag)
                    .commitNow()
            }
        }

        val bitmap = suspendCoroutine<android.graphics.Bitmap> { continuation ->
            val action: (Result<android.graphics.Bitmap>) -> Unit = { continuation.resumeWith(it) }
            when (source) {
                MediaSource.GALLERY -> imagePickerFragment.pickGalleryImage(action)
                MediaSource.CAMERA -> imagePickerFragment.pickCameraImage(action)
            }
        }

        return AndroidBitmap(bitmap)
    }

    override suspend fun pickMedia(): Media {
        val fragmentManager =
            fragmentManager ?: throw IllegalStateException("can't pick image without active window")

        permissionsController.requestPermission(Permission.GALLERY)

        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(pickerFragmentTag)
        val pickerFragment: MediaPickerFragment = if (currentFragment != null && currentFragment is MediaPickerFragment) {
            currentFragment
        } else {
            MediaPickerFragment().apply {
                fragmentManager
                    .beginTransaction()
                    .add(this, pickerFragmentTag)
                    .commitNow()
            }
        }

        return suspendCoroutine { continuation ->
            val action: (Result<Media>) -> Unit = { continuation.resumeWith(it) }
            pickerFragment.pickMedia(action)
        }
    }

    override suspend fun pickFiles(): FileMedia {
        val fragmentManager =
            fragmentManager ?: throw IllegalStateException("can't pick image without active window")

        permissionsController.requestPermission(Permission.STORAGE)

        val currentFragment: Fragment? = fragmentManager.findFragmentByTag(filePickerFragmentTag)
        val pickerFragment: FilePickerFragment = if (currentFragment != null && currentFragment is FilePickerFragment) {
            currentFragment
        } else {
            FilePickerFragment().apply {
                fragmentManager
                    .beginTransaction()
                    .add(this, pickerFragmentTag)
                    .commitNow()
            }
        }

        val path = suspendCoroutine<FileMedia> { continuation ->
            val action: (Result<FileMedia>) -> Unit = { continuation.resumeWith(it) }
            pickerFragment.pickFile(action)
        }

        return path
    }

    private fun MediaSource.requiredPermissions(): List<Permission> {
        return when (this) {
            MediaSource.GALLERY -> listOf(Permission.GALLERY)
            MediaSource.CAMERA -> listOf(Permission.CAMERA)
        }
    }
}
