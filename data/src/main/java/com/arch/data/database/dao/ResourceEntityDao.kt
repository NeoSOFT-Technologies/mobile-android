package com.arch.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.arch.data.database.dao.base.BaseDao
import com.arch.data.entity.local.ResourceEntity

@Dao
abstract class ResourceEntityDao : BaseDao<ResourceEntity> {
    @Query("SELECT * FROM resources WHERE id=:id LIMIT 1")
    abstract suspend fun getResource(id: Int): ResourceEntity?
}