package com.arch.data.database.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obj: List<T>):List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T?):Long
}
