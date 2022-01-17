package com.arch.data.source.user.remote

import com.arch.data.entity.remote.response.UserResponseEntity
import com.arch.data.entity.request.LoginRequestEntity
import com.arch.data.network.RetrofitAppServices
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val retrofitAppServices: RetrofitAppServices,
) : UserRemoteDataSource {
    override suspend fun loginUser(loginRequestEntity: LoginRequestEntity): Response<UserResponseEntity> {
        return retrofitAppServices.login(
            loginRequestEntity = loginRequestEntity
        )
    }
}