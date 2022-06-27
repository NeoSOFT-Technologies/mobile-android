package com.arch.data.entity.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResourceDetailResponseEntity(
    @Json(name = "data")
    val data: ResourceDataResponseEntity?,
)