package com.arch.usecase

import com.arch.entity.User
import com.arch.error.AppError
import com.arch.error.AppErrorType
import com.arch.error.BaseError
import com.arch.repository.UserRepository
import com.arch.usecase.base.BaseUseCase
import com.arch.usecase.base.Params
import com.arch.utils.Either
import com.arch.utils.Validator
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: UserRepository,
) : BaseUseCase<LoginUseCase.AuthParams, Either<BaseError, User>> {

    override suspend fun execute(params: AuthParams): Either<BaseError, User> {
        return authRepository.login(params.username, params.password)
    }

    class AuthParams(
        val username: String,
        val password: String
    ) : Params {
        override fun verify(): Boolean {
            return if (Validator.isEmpty(username)) throw AppError(
                appErrorType = AppErrorType.EmailEmpty,
            )
            else if (!Validator.isValidEmail(username)) throw AppError(
                appErrorType = AppErrorType.InvalidEmail,
            )
            else if (Validator.isEmpty(password)) throw AppError(
                appErrorType = AppErrorType.PasswordEmpty,
            )
            else true
        }

    }

}
