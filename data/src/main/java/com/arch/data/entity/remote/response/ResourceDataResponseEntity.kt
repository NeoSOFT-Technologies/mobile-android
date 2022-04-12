package com.arch.data.entity.remote.response

import com.arch.entity.ResourceData
import com.arch.utils.mapper.BaseLayerDataTransformer
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResourceDataResponseEntity(
    @SerializedName(value = "id")
    val id: Int?,
    @SerializedName(value = "name")
    val name: String?,
    @SerializedName(value = "year")
    val year: Int?,
    @SerializedName(value = "pantone_value")
    val pantoneValue: String?,
) : BaseLayerDataTransformer<ResourceDataResponseEntity, ResourceData>() {
    override fun transform(): ResourceData {
        return ResourceData(
            id = id,
            name = name,
            year = year,
            pantoneValue = pantoneValue,
        )
    }

    override fun transform(from: Collection<ResourceDataResponseEntity>): Collection<ResourceData> {
        return super.transform(from)
    }
}