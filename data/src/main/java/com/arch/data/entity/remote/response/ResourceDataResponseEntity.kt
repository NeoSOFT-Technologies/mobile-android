package com.arch.data.entity.remote.response

import com.core.entity.ResourceData
import com.core.utils.mapper.BaseLayerDataTransformer
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResourceDataResponseEntity(
    @Json(name = "name")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "year")
    val year: Int?,
    @Json(name = "pantone_value")
    val pantoneValue: String?,
) : BaseLayerDataTransformer<ResourceDataResponseEntity, ResourceData>() {
    override fun transform(): ResourceData {
        return ResourceData(
            id,
            name,
            year,
            pantoneValue,
        )
    }

    override fun transform(from: Collection<ResourceDataResponseEntity>): Collection<ResourceData> {
        return super.transform(from)
    }
}