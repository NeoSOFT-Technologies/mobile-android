package com.arch.data.source.user.remote

import com.arch.data.entity.remote.response.UserResponseEntity
import com.arch.data.entity.request.LoginRequestEntity
import retrofit2.Response

interface UserRemoteDataSource {
   suspend fun loginUser(loginRequestEntity: LoginRequestEntity): Response<UserResponseEntity>
}