package com.arch.medialibrary

data class Media(
    val name: String,
    val path: String,
    val preview: Bitmap,
    val type: MediaTypes
)