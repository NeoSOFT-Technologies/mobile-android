package com.arch.medialibrary.picker

import com.arch.medialibrary.MediaBitmap
import com.arch.medialibrary.FileMedia
import com.arch.medialibrary.Media
import com.arch.permissions.IPermissionsController

 const val DEFAULT_MAX_IMAGE_WIDTH = 1024
 const val DEFAULT_MAX_IMAGE_HEIGHT = 1024

interface MediaPickerController {
    val permissionsController: IPermissionsController

    suspend fun pickImage(source: MediaSource): MediaBitmap
    suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): MediaBitmap
    suspend fun pickMedia(): Media
    suspend fun pickFiles(): FileMedia
}