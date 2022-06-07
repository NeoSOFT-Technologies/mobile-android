package com.arch.medialibrary.picker

import com.arch.medialibrary.Bitmap
import com.arch.medialibrary.FileMedia
import com.arch.medialibrary.Media
import com.arch.permissions.IPermissionsController

internal const val DEFAULT_MAX_IMAGE_WIDTH = 1024
internal const val DEFAULT_MAX_IMAGE_HEIGHT = 1024

interface MediaPickerController {
    val permissionsController: IPermissionsController

    suspend fun pickImage(source: MediaSource): Bitmap
    suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): Bitmap
    suspend fun pickMedia(): Media
    suspend fun pickFiles(): FileMedia
}