package com.core.repository

import com.core.entity.User
import com.core.error.BaseError
import com.core.utils.Either

interface UserRepository {
    suspend fun login(username: String, password: String): Either<BaseError, User>
}