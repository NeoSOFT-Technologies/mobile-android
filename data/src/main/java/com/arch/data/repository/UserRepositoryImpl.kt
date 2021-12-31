package com.arch.data.repository

import com.arch.data.network.utils.safeApiCall
import com.arch.data.source.user.local.UserLocalDataSource
import com.arch.data.source.user.remote.UserRemoteDataSource
import com.core.entity.User
import com.core.error.BaseError
import com.core.error.NetworkError
import com.core.repository.UserRepository
import com.core.utils.Either
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
) : UserRepository {
    override suspend fun login(username: String, password: String): Either<BaseError, User> {
        return when (val response = safeApiCall {
            userRemoteDataSource.loginUser(
                username, password
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