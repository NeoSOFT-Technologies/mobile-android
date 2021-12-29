package com.arch.data.database.dao

import androidx.room.Dao
import com.arch.data.database.dao.base.BaseDao
import com.arch.data.entity.local.UserEntity

@Dao
abstract class UserEntityDao:BaseDao<UserEntity>