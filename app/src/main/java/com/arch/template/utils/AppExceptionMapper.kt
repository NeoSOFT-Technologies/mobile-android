package com.arch.template.utils

import android.content.Context
import com.arch.error.mappers.ExceptionMappers
import com.arch.template.R
import com.core.error.AppError
import com.core.error.AppErrorType
import com.core.error.NetworkError

object AppExceptionMapper {
    fun init(context: Context) {
        ExceptionMappers
            .condition(
                condition = { it is NetworkError },
                mapper = {
                    (it as NetworkError).message ?: ""
                }
            )
            .condition(
                condition = {
                    (it is AppError) && it.appErrorType == AppErrorType.EmailEmpty
                },
                mapper = {
                    context.getString(R.string.app_error_empty_email)
                }
            )
            .condition(
                condition = {
                    (it is AppError) && it.appErrorType == AppErrorType.PasswordEmpty
                },
                mapper = {
                    context.getString(R.string.app_error_empty_password)
                }
            )
            .condition(
                condition = {
                    (it is AppError) && it.appErrorType == AppErrorType.InvalidEmail
                },
                mapper = {
                    context.getString(R.string.app_error_invalid_email)
                }
            )
            .register<IllegalArgumentException, String> {
                context.getString(R.string.dashboard_label_header_app)
            }
    }
}