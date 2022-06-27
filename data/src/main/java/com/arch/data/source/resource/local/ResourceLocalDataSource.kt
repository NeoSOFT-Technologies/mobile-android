package com.arch.data.source.resource.local

import com.arch.data.entity.local.ResourceEntity

interface ResourceLocalDataSource {
    suspend fun saveResourceInfo(resourceEntity: ResourceEntity):Boolean
    suspend fun getResourceInfo(id:Int): ResourceEntity?
}