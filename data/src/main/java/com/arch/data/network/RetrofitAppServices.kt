package com.arch.data.network

import com.arch.data.entity.remote.response.UserResponseEntity
import com.arch.data.entity.request.LoginRequestEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitAppServices {
    @POST("/api/login")
    suspend fun login(@Body loginRequestEntity: LoginRequestEntity): Response<UserResponseEntity>
}