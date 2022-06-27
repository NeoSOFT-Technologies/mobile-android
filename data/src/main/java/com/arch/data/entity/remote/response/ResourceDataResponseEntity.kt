package com.arch.data.entity.remote.response

import androidx.room.PrimaryKey
import com.arch.data.entity.local.ResourceEntity
import com.arch.utils.mapper.BaseLayerDataTransformer
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResourceDataResponseEntity(
    @PrimaryKey
    @SerializedName(value = "id")
    val id: Int,
    @SerializedName(value = "name")
    val name: String?,
    @SerializedName(value = "year")
    val year: Int?,
    @SerializedName(value = "pantone_value")
    val pantoneValue: String?,
) : BaseLayerDataTransformer<ResourceDataResponseEntity, ResourceEntity>() {

    override fun transform(): ResourceEntity {
        return ResourceEntity(
            id = id,
            name = name,
            year = year,
            pantoneValue = pantoneValue,
        )
    }

    override fun transform(from: Collection<ResourceDataResponseEntity>): Collection<ResourceEntity> {
        return super.transform(from)
    }
}