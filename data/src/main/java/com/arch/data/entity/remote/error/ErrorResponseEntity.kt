package com.core.entity.remote.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponseEntity(
    @Json(name = "error") var error: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Error(
        @Json(name = "code") var code: String?,
        @Json(name = "message") var message: String?,
        @Json(name = "msg") var msg: String?,
        @Json(name = "requestId") var requestId: String?
    )
}
