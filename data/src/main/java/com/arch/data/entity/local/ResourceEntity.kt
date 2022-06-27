package com.arch.data.entity.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arch.data.entity.remote.response.ResourceDataResponseEntity
import com.arch.entity.ResourceData
import com.arch.utils.mapper.BaseLayerDataTransformer

@Entity(tableName = "resources")
data class ResourceEntity(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val year: Int?,
    val pantoneValue: String?,
): BaseLayerDataTransformer<ResourceEntity, ResourceData>() {
    override fun transform(): ResourceData {
        return ResourceData(
            id = id,
            name = name,
            year = year,
            pantoneValue = pantoneValue,
        )
    }

    override fun transform(from: Collection<ResourceEntity>): Collection<ResourceData> {
        return super.transform(from)
    }
}
