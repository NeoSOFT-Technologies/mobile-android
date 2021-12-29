package com.arch.data.entity.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestEntity(
    @Json(name = "email")
    var email: String? = "",
    @Json(name = "password")
    var password: String? = "",
)