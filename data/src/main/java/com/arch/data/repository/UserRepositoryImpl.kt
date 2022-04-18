package com.arch.data.repository

import com.arch.data.entity.request.LoginRequestEntity
import com.arch.data.network.utils.safeApiCall
import com.arch.data.source.user.local.UserLocalDataSource
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.arch.entity.User
import com.arch.error.BaseError
import com.arch.error.NetworkError
import com.arch.repository.UserRepository
import com.arch.utils.Either
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun login(username: String, password: String): Either<BaseError, User> {
        return when (val response = safeApiCall {
            userRemoteDataSource.loginUser(
                LoginRequestEntity(username, password)
            )
        }) {
            is Either.Left -> Either.Left(
                response.left
            )
            is Either.Right -> {
                response.right.body()?.let {
                    userLocalDataSource.saveUserInfo(it.transform())
                    Either.Right(
                        User(
                            response.right.body()?.token,
                        )
                    )
                } ?: kotlin.run {
                    return Either.Left(
                        NetworkError(
                            message = response.right.message(),
                            httpError = response.right.code()
                        )
                    )
                }
            }
        }
    }
}