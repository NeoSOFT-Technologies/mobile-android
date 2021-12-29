package com.arch.data.source.user.remote

import com.arch.data.entity.remote.response.UserResponseEntity
import retrofit2.Response

interface UserRemoteDataSource {
   suspend fun loginUser(email:String,password:String):Response<UserResponseEntity>
}