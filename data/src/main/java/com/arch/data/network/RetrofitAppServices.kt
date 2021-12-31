package com.arch.data.network

import com.arch.data.entity.remote.response.PageInfoResponseEntity
import com.arch.data.entity.remote.response.UserResponseEntity
import com.arch.data.entity.request.LoginRequestEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitAppServices {
    @POST("/api/login")
    suspend fun login(@Body loginRequestEntity: LoginRequestEntity): Response<UserResponseEntity>

    @GET("/api/unknown")
    suspend fun getResourceData(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<PageInfoResponseEntity>
}