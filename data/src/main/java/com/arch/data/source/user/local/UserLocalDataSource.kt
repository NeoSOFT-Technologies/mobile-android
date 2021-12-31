package com.arch.data.source.user.local

import com.arch.data.entity.local.UserEntity

interface UserLocalDataSource {
    suspend fun saveUserInfo(userEntity: UserEntity):Boolean
}