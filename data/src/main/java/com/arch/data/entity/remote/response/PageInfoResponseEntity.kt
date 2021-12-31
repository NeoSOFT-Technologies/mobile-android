package com.arch.data.entity.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageInfoResponseEntity(
    @Json(name = "page")
    val page: Int?,
    @Json(name = "per_page")
    val perPage: Int?,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "data")
    val data: List<ResourceDataResponseEntity>?,
)