package com.arch.data.entity.remote.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponseEntity(
    @Json(name = "error") var error: String? = null
)
