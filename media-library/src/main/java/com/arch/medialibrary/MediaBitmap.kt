package com.arch.medialibrary

interface MediaBitmap {
    fun toByteArray(): ByteArray
    fun toBase64(): String
    fun toBase64WithCompress(maxSize: Int): String
}

private const val BASE64_IMAGE_MIME_PREFIX = "data:image/png;base64,"

fun MediaBitmap.toBase64WithCompressMIME(maxSize: Int) =
    "$BASE64_IMAGE_MIME_PREFIX${toBase64WithCompress(maxSize)}"

fun MediaBitmap.toBase64MIME() = "$BASE64_IMAGE_MIME_PREFIX${toBase64()}"