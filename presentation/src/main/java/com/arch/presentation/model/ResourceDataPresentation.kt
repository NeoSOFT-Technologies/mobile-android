package com.arch.presentation.model

import com.arch.entity.ResourceData
import com.arch.utils.mapper.BaseLayerDataTransformer

data class ResourceDataPresentation(
    val id: Int? = -1,
    val name: String? = "",
    val year: Int? = -1,
    val pantoneValue: String? = "",
) : BaseLayerDataTransformer<ResourceDataPresentation, ResourceData>() {
    override fun restore(data: ResourceData): ResourceDataPresentation {
        return ResourceDataPresentation(
            id = data.id,
            name = data.name,
            year = data.year,
            pantoneValue = data.pantoneValue,
        )
    }
}