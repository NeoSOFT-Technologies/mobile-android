package com.arch.repository

import com.arch.entity.User
import com.arch.error.BaseError
import com.arch.utils.Either

interface UserRepository {
    suspend fun login(username: String, password: String): Either<BaseError, User>
}