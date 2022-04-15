package com.arch.presentation.model

import com.arch.entity.ResourceData
import com.arch.utils.mapper.BaseLayerDataTransformer

data class ResourceDataModel(
    val id: Int? = -1,
    val name: String? = "",
    val year: Int? = -1,
    val pantoneValue: String? = "",
) : BaseLayerDataTransformer<ResourceDataModel, ResourceData>() {
    override fun restore(data: ResourceData): ResourceDataModel {
        return ResourceDataModel(
            id = data.id,
            name = data.name,
            year = data.year,
            pantoneValue = data.pantoneValue,
        )
    }
}