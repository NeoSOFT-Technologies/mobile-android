package com.arch.data.source.user.local

import com.arch.data.database.dao.UserEntityDao
import com.arch.data.entity.local.UserEntity
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(private val userEntityDao: UserEntityDao) :
    UserLocalDataSource {
    override suspend fun saveUserInfo(userEntity: UserEntity) :Boolean{
        return userEntityDao.insert(userEntity)>0
    }
}