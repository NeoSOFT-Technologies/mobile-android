package com.arch.data.source.resource.local

import com.arch.data.database.dao.ResourceEntityDao
import com.arch.data.entity.local.ResourceEntity
import javax.inject.Inject

class ResourceLocalDataSourceImpl @Inject constructor(private val resourceEntityDao: ResourceEntityDao) :
    ResourceLocalDataSource {
    override suspend fun saveResourceInfo(resourceEntity: ResourceEntity): Boolean {
        try{
            print("saveResourceInfo")
            resourceEntityDao.insert(resourceEntity) > 0
        }catch (e:Exception){
            print("Print exception ${e}")
        }
        return true
    }

    override suspend fun getResourceInfo(id: Int): ResourceEntity? {
        return resourceEntityDao.getResource(id)
    }
}