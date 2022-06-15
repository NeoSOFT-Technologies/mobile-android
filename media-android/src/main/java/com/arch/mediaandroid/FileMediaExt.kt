package com.arch.mediaandroid

import com.arch.medialibrary.FileMedia
import java.io.File

fun FileMedia.toByteArray(): ByteArray {
    val file = File(path)
    return file.readBytes()
}